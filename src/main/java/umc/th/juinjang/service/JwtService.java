package umc.th.juinjang.service;

import ch.qos.logback.core.spi.ErrorCodes;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import umc.th.juinjang.apiPayload.ExceptionHandler;
import umc.th.juinjang.apiPayload.code.status.ErrorStatus;
import umc.th.juinjang.jwt.JwtAuthenticationFilter;
import umc.th.juinjang.model.dto.auth.TokenDto;
import umc.th.juinjang.repository.limjang.MemberRepository;
import umc.th.juinjang.service.auth.UserDetailServiceImpl;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.secret}")
    private String JWT_SECRET;
    private final UserDetailServiceImpl userDetailService;
    private Long tokenValidTime = 1000L * 60 * 60; // 1h
    private Long refreshTokenValidTime = 1000L * 60 * 60 * 24 * 7; // 7d

    private MemberRepository memberRepository;

    // access token 생성
    public String encodeJwtToken(TokenDto tokenDto) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("juinjang")
                .setIssuedAt(now)
                .setSubject(tokenDto.getMemberId().toString())
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .claim("memberId", tokenDto.getMemberId())
                .signWith(SignatureAlgorithm.HS256,
                        Base64.getEncoder().encodeToString(("" + JWT_SECRET).getBytes(
                                StandardCharsets.UTF_8)))
                .compact();
    }

    // refresh token 생성
    public String encodeJwtRefreshToken(Long memberId) {
        Date now = new Date();
        return Jwts.builder()
                .setIssuedAt(now)
                .setSubject(memberId.toString())
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                .claim("memberId", memberId)
                .claim("roles", "USER")
                .signWith(SignatureAlgorithm.HS256,
                        Base64.getEncoder().encodeToString(("" + JWT_SECRET).getBytes(
                                StandardCharsets.UTF_8)))
                .compact();
    }

    // JWT 토큰 으로부터 memberId 추출
    public Long getMemberIdFromJwtToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(Base64.getEncoder().encodeToString(("" + JWT_SECRET).getBytes(
                            StandardCharsets.UTF_8)))
                    .parseClaimsJws(token)
                    .getBody();
            return Long.parseLong(claims.getSubject());
        } catch(Exception e) {
            throw new ExceptionHandler(ErrorStatus.TOKEN_UNAUTHORIZED);
        }
    }


    // Autorization : Bearer에서 token 추출 (refreshToken, accessToken 포함)
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(JwtAuthenticationFilter.AUTHORIZATION_HEADER);

        if(bearerToken == null)
            throw new ExceptionHandler(ErrorStatus.TOKEN_EMPTY);
        else if(StringUtils.isNotEmpty(bearerToken) && bearerToken.startsWith("Bearer "));
            return bearerToken.substring(7);
    }

    // 토큰 유효성 + 만료일자 확인
    public Boolean validateToken(String token) {
        Date now = new Date();

        try{
            // 주어진 토큰을 파싱하고 검증.
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(Base64.getEncoder().encodeToString(("" + JWT_SECRET).getBytes(StandardCharsets.UTF_8)))
                    .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date(now.getTime()));
        }catch (Exception e){
            throw new ExceptionHandler(ErrorStatus.TOKEN_UNAUTHORIZED);
        }
    }

    // JWT 토큰 인증 정보 조회 (토큰 복호화)
    public Authentication getAuthentication(String token) {
       // UserDetails userDetails = .loadUserByUsername(this.getMemberIdFromJwtToken(token).toString());
        //memberRepository에서
        UserDetails userDetails = userDetailService.loadUserByUsername(this.getMemberIdFromJwtToken(token).toString());
//        UserDetails userDetails =ngull;
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰 유효 시간 확인
    public Long getExpiration(String accessToken) {
        Date expiration = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(accessToken).getBody().getExpiration();
        Long now = new Date().getTime();

        return (expiration.getTime() - now);
    }
}
