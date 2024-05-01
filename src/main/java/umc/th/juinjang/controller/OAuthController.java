package umc.th.juinjang.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import umc.th.juinjang.apiPayload.ApiResponse;
import umc.th.juinjang.apiPayload.ExceptionHandler;
import umc.th.juinjang.apiPayload.exception.handler.MemberHandler;
import umc.th.juinjang.model.dto.auth.LoginResponseDto;
import umc.th.juinjang.model.dto.auth.kakao.KakaoLoginRequestDto;
import umc.th.juinjang.service.JwtService;
import umc.th.juinjang.service.auth.OAuthService;

import java.io.IOException;

import static umc.th.juinjang.apiPayload.code.status.ErrorStatus.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
public class OAuthController {

    private final OAuthService oauthService;
    

    // 카카오 로그인
    // 프론트 측에서 전달해준 사용자 정보로 토큰 발급
    @PostMapping("/kakao")
    public ApiResponse<LoginResponseDto> kakaoLogin(@RequestBody KakaoLoginRequestDto kakaoReqDto) {
        return ApiResponse.onSuccess(oauthService.kakaoLogin(kakaoReqDto));
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

    //애플 로그인 컨트롤러
    // publicKey 클라이언트로부터 받아야함

//    @PostMapping
//    public ApiResponse<String>

}