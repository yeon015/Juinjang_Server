package umc.th.juinjang.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.micrometer.common.lang.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import umc.th.juinjang.apiPayload.ApiResponse;
import umc.th.juinjang.apiPayload.ExceptionHandler;
import umc.th.juinjang.apiPayload.code.status.SuccessStatus;
import umc.th.juinjang.apiPayload.exception.handler.MemberHandler;
import umc.th.juinjang.model.dto.auth.LoginResponseDto;
import umc.th.juinjang.model.dto.auth.apple.AppleLoginRequestDto;
import umc.th.juinjang.model.dto.auth.apple.AppleSignUpRequestDto;
import umc.th.juinjang.model.dto.auth.kakao.KakaoLoginRequestDto;
import umc.th.juinjang.model.dto.auth.kakao.KakaoSignUpRequestDto;
import umc.th.juinjang.model.entity.Member;
import umc.th.juinjang.service.JwtService;
import umc.th.juinjang.service.auth.OAuthService;

import java.io.IOException;

import static umc.th.juinjang.apiPayload.code.status.ErrorStatus.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
public class OAuthController {

    private final OAuthService oauthService;
    

    // 카카오 로그인
    // 프론트 측에서 전달해준 사용자 정보로 토큰 발급
    @PostMapping("/kakao/login")
    public ApiResponse<LoginResponseDto> kakaoLogin(@RequestBody @Validated KakaoLoginRequestDto kakaoReqDto) {
        return ApiResponse.onSuccess(oauthService.kakaoLogin(kakaoReqDto));
    }

    // 카카오 로그인 (회원가입)
    @PostMapping("/kakao/signup")
    public ApiResponse<LoginResponseDto> kakaoSignUp(@RequestBody @Validated KakaoSignUpRequestDto kakaoSignUpReqDto) {
        return ApiResponse.onSuccess(oauthService.kakaoSignUp(kakaoSignUpReqDto));
    }

    // refreshToken으로 accessToken 재발급
    // Authorization : Bearer Token에 refreshToken 담기
    @PostMapping("/regenerate-token")
    public ApiResponse<LoginResponseDto> regenerateAccessToken(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization");
        String refreshToken = request.getHeader("Refresh-Token");

        System.out.println("Access Token: " + accessToken.substring(7));
        System.out.println("Refresh Token: " + refreshToken.substring(7));

        if (StringUtils.hasText(accessToken) && accessToken.startsWith("Bearer ") && StringUtils.hasText(refreshToken) && refreshToken.startsWith("Bearer ")) {
            LoginResponseDto result = oauthService.regenerateAccessToken(accessToken.substring(7), refreshToken.substring(7));
            return ApiResponse.onSuccess(result);
        } else
            throw new ExceptionHandler(TOKEN_EMPTY);
    }

    // 로그아웃 -> refresh 토큰 만료
    @PostMapping("/logout")
    public ApiResponse<String> logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            String result = oauthService.logout(token.substring(7));
            return ApiResponse.onSuccess(result);
        } else
            throw new ExceptionHandler(TOKEN_EMPTY);
    }

    // 애플 로그인
    // 클라이언트에서 identity token 값 받아오기
    // 사용자가 입력한 정보를 바탕으로 Apple ID servers 에게 Identity Token 발급 요청 (프론트가) -> 이를 우리 서버가 가져오는 것
    // Identity Token 값을 바탕으로 사용자 식별 & refresh, access Token 발급해주고 DB 저장 (로그인하기)

    // 로그인
    @PostMapping("/apple/login")
    public ApiResponse<LoginResponseDto> appleLogin(@RequestBody @Validated AppleLoginRequestDto appleReqDto) {
        if (appleReqDto.getIdentityToken() == null)
            throw new ExceptionHandler(APPLE_ID_TOKEN_EMPTY);
        return ApiResponse.onSuccess(oauthService.appleLogin(appleReqDto));
    }

    @PostMapping("/apple/signup")
    public ApiResponse<LoginResponseDto> appleSignUp(@RequestBody @Validated AppleSignUpRequestDto appleSignUpReqDto) {
        if (appleSignUpReqDto.getIdentityToken() == null)
            throw new ExceptionHandler(APPLE_ID_TOKEN_EMPTY);
        return ApiResponse.onSuccess(oauthService.appleSignUp(appleSignUpReqDto));
    }

    // 카카오 탈퇴
    @DeleteMapping("/withdraw/kakao")
    public ApiResponse kakaoWithdraw(@AuthenticationPrincipal Member member, @RequestHeader("target-id") String kakaoTargetId) {
        Long targetId;

        if(kakaoTargetId == null) {
            throw new ExceptionHandler(EMPTY_TARGET_ID);
        } else {
            targetId = Long.parseLong(kakaoTargetId);
            log.info("target_id 변환 : " + targetId);
            log.info("변환된 target_id type : " + targetId.getClass().getName());
            log.info("=======================");
            log.info("member email : " + member.getEmail());
            log.info("member의 target_id : " + member.getKakaoTargetId());

            if(!targetId.equals(member.getKakaoTargetId())) {
                log.info("target_id 다름");
                throw new ExceptionHandler(UNCORRECTED_TARGET_ID);
            }
        }

        log.info("target_id 같음");

        // 카카오 계정 연결 끊기
        boolean isUnlink = oauthService.kakaoWithdraw(member, targetId);

        // 사용자 정보 삭제 (DB)
        if (!isUnlink) {
            throw new ExceptionHandler(NOT_UNLINK_KAKAO);
        }
        oauthService.deleteMember(member);

        return ApiResponse.onSuccess(SuccessStatus.MEMBER_DELETE);
    }


    // 애플 탈퇴
    @DeleteMapping("/withdraw/apple")
    public ApiResponse withdraw(@AuthenticationPrincipal Member member,
                                      @Nullable@RequestHeader("X-Apple-Code") final String code){
        oauthService.appleWithdraw(member, code);
        return ApiResponse.onSuccess(SuccessStatus.MEMBER_DELETE);
    }
}