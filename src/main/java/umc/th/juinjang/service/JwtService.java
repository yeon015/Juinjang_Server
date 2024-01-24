package umc.th.juinjang.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {


    // Autorization : Bearer에서 token 추출 (refreshToken, accessToken 포함)
    public String resolveToken(HttpServletRequest request) {
        return null;
    }

    // 토큰 유효성 + 만료일자 확인
    public Boolean validateToken(String token) {
        return null;
    }

    // JWT 토큰 인증 정보 조회 (토큰 복호화)
    public Authentication getAuthentication(String token) {
        return null;
    }
}
