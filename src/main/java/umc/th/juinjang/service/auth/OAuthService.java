package umc.th.juinjang.service.auth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import umc.th.juinjang.apiPayload.ExceptionHandler;
import umc.th.juinjang.apiPayload.code.status.ErrorStatus;
import umc.th.juinjang.apiPayload.exception.handler.MemberHandler;
import umc.th.juinjang.model.dto.auth.LoginResponseDto;
import umc.th.juinjang.model.dto.auth.TokenDto;
import umc.th.juinjang.model.dto.auth.kakao.KakaoLoginRequestDto;
import umc.th.juinjang.model.entity.Member;
import umc.th.juinjang.model.entity.enums.MemberProvider;
import umc.th.juinjang.repository.limjang.MemberRepository;
import umc.th.juinjang.service.JwtService;

import java.time.LocalDateTime;
import java.util.Optional;

import static umc.th.juinjang.apiPayload.code.status.ErrorStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthService {

    private final MemberRepository memberRepository;
    private final JwtService jwtService;

    // 카카오 로그인
    // 프론트에서 받은 사용자 정보로 accessToken, refreshToken 발급
    @Transactional
    public LoginResponseDto kakaoLogin(KakaoLoginRequestDto kakaoReqDto) {
        String email = kakaoReqDto.getEmail();
        log.info(kakaoReqDto.getEmail());

        if(email == null)
            throw new MemberHandler(ErrorStatus.MEMBER_EMAIL_NOT_FOUND);

        Optional<Member> getMember = memberRepository.findByEmail(email);
        Member member;
        if(getMember.isPresent()){  // 이미 회원가입한 회원인 경우
            member = getMember.get();
            if(!member.getProvider().equals(MemberProvider.KAKAO))   // 이미 회원가입했지만 Kakao가 아닌 다른 소셜 로그인 사용
                throw new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND_IN_KAKAO);
        } else {    // 아직 회원가입 하지 않은 회원인 경우
            member = memberRepository.save(
                    Member.builder()
                            .email(email)
                            .provider(MemberProvider.KAKAO)
                            .nickname(kakaoReqDto.getNickname())
                            .refreshToken("")
                            .refreshTokenExpiresAt(LocalDateTime.now())
                            .build()
            );
            System.out.println("member id : " + member.getMemberId());
            System.out.println("member email : " + member.getEmail());
            System.out.println("member nickname : " + member.getNickname());
        }

        // accessToken, refreshToken 발급
        String newAccessToken = jwtService.encodeJwtToken(new TokenDto(member.getMemberId()));
        String newRefreshToken = jwtService.encodeJwtRefreshToken(member.getMemberId());

        System.out.println("newAccessToken : " + newAccessToken);
        System.out.println("newRefreshToken : " + newRefreshToken);

        // DB에 refreshToken 저장
        member.updateRefreshToken(newRefreshToken);
        memberRepository.save(member);

        System.out.println("member nickname : " + member.getNickname());

        return new LoginResponseDto(newAccessToken, newRefreshToken, member.getEmail());
    }

    // refreshToken으로 accessToken 발급하기
    @Transactional
    public LoginResponseDto regenerateAccessToken(String accessToken, String refreshToken) {
        if(jwtService.validateTokenBoolean(accessToken))  // access token 유효성 검사
            throw new ExceptionHandler(ACCESS_TOKEN_AUTHORIZED);

        if(!jwtService.validateToken(refreshToken))  // refresh token 유효성 검사
            throw new ExceptionHandler(REFRESH_TOKEN_UNAUTHORIZED);

        Long memberId = jwtService.getMemberIdFromJwtToken(refreshToken);
        log.info("memberId : " + memberId);

        Optional<Member> getMember = memberRepository.findById(memberId);
        if(getMember.isEmpty())
            throw new MemberHandler(MEMBER_NOT_FOUND);

        Member member = getMember.get();
        if(!refreshToken.equals(member.getRefreshToken()))
            throw new ExceptionHandler(REFRESH_TOKEN_UNAUTHORIZED);

        String newRefreshToken = jwtService.encodeJwtRefreshToken(memberId);
        String newAccessToken = jwtService.encodeJwtToken(new TokenDto(memberId));

        member.updateRefreshToken(newRefreshToken);
        memberRepository.save(member);

        System.out.println("member nickname : " + member.getNickname());

        return new LoginResponseDto(newAccessToken, newRefreshToken, member.getNickname());
    }

    // 로그아웃
    @Transactional
    public String logout(String refreshToken) {
        Optional<Member> getMember = memberRepository.findByRefreshToken(refreshToken);
        if(getMember.isEmpty())
            throw new MemberHandler(MEMBER_NOT_FOUND);

        Member member = getMember.get();
        if(member.getRefreshToken().equals(""))
            throw new MemberHandler(ALREADY_LOGOUT);

        member.refreshTokenExpires();
        memberRepository.save(member);

        return "로그아웃 성공";
    }
}