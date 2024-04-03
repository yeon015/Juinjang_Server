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
    private final JwtService jwtService;

//    // 카카오 로그인
//    // 사용자 로그인 페이지 제공 단계 - url
//    @GetMapping(value="/{socialLoginType}")
//    public void socialLoginType(@PathVariable(name="socialLoginType") String socialLoginType) throws IOException {
//        oauthService.request(socialLoginType);
//    }
//
//    // code -> accessToken 받아오기
//    // accessToken -> 사용자 정보 받아오기
//    @GetMapping(value="/{socialLoginType}/callback")
//    public ApiResponse<LoginResponseDto> callback(
//            @PathVariable(name="socialLoginType") String socialLoginType,
//            @RequestParam(name="code") String code) throws JsonProcessingException {
//
//        LoginResponseDto result = oauthService.oauthLogin(socialLoginType, code);
//        return ApiResponse.onSuccess(result);
//    }

    // 카카오 로그인
    // 프론트 측에서 사용자 정보 전달
    @PostMapping("/{socialLoginType}")
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
}