package umc.th.juinjang.service.auth;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import umc.th.juinjang.apiPayload.ExceptionHandler;
import umc.th.juinjang.apiPayload.code.status.ErrorStatus;
import umc.th.juinjang.apiPayload.exception.handler.MemberHandler;
import umc.th.juinjang.model.dto.auth.LoginResponseDto;
import umc.th.juinjang.model.dto.auth.TokenDto;
import umc.th.juinjang.model.dto.auth.apple.AppleInfo;
import umc.th.juinjang.model.dto.auth.apple.AppleLoginRequestDto;
import umc.th.juinjang.model.dto.auth.apple.AppleSignUpRequestDto;
import umc.th.juinjang.model.dto.auth.kakao.KakaoLoginRequestDto;
import umc.th.juinjang.model.dto.auth.kakao.KakaoSignUpRequestDto;
import umc.th.juinjang.model.entity.Member;
import umc.th.juinjang.model.entity.enums.MemberProvider;
import umc.th.juinjang.repository.limjang.MemberRepository;
import umc.th.juinjang.service.JwtService;

import javax.naming.AuthenticationException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.util.Optional;

import static umc.th.juinjang.apiPayload.code.status.ErrorStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthService {

    private final MemberRepository memberRepository;
    private final JwtService jwtService;

    // 카카오 로그인 (회원가입된 경우)
    // 프론트에서 받은 사용자 정보로 accessToken, refreshToken 발급
    @Transactional
    public LoginResponseDto kakaoLogin(KakaoLoginRequestDto kakaoReqDto) {
        String email = kakaoReqDto.getEmail();
        log.info(kakaoReqDto.getEmail());

        if(email == null)
            throw new MemberHandler(MEMBER_EMAIL_NOT_FOUND);

        Optional<Member> getMember = memberRepository.findByEmail(email);
        Member member;
        if(getMember.isPresent()){  // 이미 회원가입한 회원인 경우
            member = getMember.get();
            if(!member.getProvider().equals(MemberProvider.KAKAO))   // 이미 회원가입했지만 Kakao가 아닌 다른 소셜 로그인 사용
                throw new MemberHandler(MEMBER_NOT_FOUND_IN_KAKAO);
        } else {    // 회원가입이 안되어있는 경우 -> 에러 발생. 회원가입 해야 함
            throw new MemberHandler(MEMBER_NOT_FOUND);
        }

        // accessToken, refreshToken 발급 후 반환
        return createToken(member);
    }

    // 카카오 로그인 (회원가입 해야하는 경우)
    @Transactional
    public LoginResponseDto kakaoSignUp (KakaoSignUpRequestDto kakaoSignUpReqDto) {
        String email = kakaoSignUpReqDto.getEmail();
        log.info(kakaoSignUpReqDto.getEmail());

        if(email == null)
            throw new MemberHandler(MEMBER_EMAIL_NOT_FOUND);

        Optional<Member> getMember = memberRepository.findByEmail(email);
        Member member;
        if(getMember.isPresent()){  // 이미 회원가입한 회원인 경우 -> 에러 발생
            throw new MemberHandler(ALREADY_MEMBER);
        } else {    // 아직 회원가입 하지 않은 회원인 경우
            member = memberRepository.save(
                    Member.builder()
                            .email(email)
                            .provider(MemberProvider.KAKAO)
                            .nickname(kakaoSignUpReqDto.getNickname())
                            .refreshToken("")
                            .refreshTokenExpiresAt(LocalDateTime.now())
                            .build()
            );
            System.out.println("member id : " + member.getMemberId());
            System.out.println("member email : " + member.getEmail());
            System.out.println("member nickname : " + member.getNickname());
        }

        // accessToken, refreshToken 발급 후 반환
        return createToken(member);
    }

    // accessToken, refreshToken 발급
    @Transactional
    public LoginResponseDto createToken(Member member) {
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
            //승연언니 이부분 원래 validateToken에서 validateTokenBoolean로 바꿨는데 괜찮을까여..(원래validateToken과 겹쳐서 그 함수는 걍 지웠어요)
            throw new ExceptionHandler(ACCESS_TOKEN_AUTHORIZED);

        if(!jwtService.validateTokenBoolean(refreshToken))  // refresh token 유효성 검사
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
    
    // 애플 로그인 (회원가입된 경우)
    @Transactional
    public LoginResponseDto appleLogin(AppleLoginRequestDto appleLoginRequest) {
        // email, sub값 추출 후 db에서 해당 email값 그리고 sub값을 가진 유저가 있는지 find
        // 1. 추출한 email, sub 값이 null이면 -> 잘못된 apple token
        // 2. db에서 각각 find한 회원 id가 다르면 에러 (올바르지 않은 정보)
        // 3. db에 email, sub값 둘 다 있으면 재로그인 (혹시 provider가 다르다면 에러)
        // 4. db에 email, sub값 둘 다 없으면 회원가입
        // 탈퇴 처리는 추후에
        log.info("Oauth service 까지 들어옴"+ appleLoginRequest.getIdentityToken());
        AppleInfo appleInfo = jwtService.getAppleAccountId(appleLoginRequest.getIdentityToken().replaceAll("\\n", ""));
        String email = appleInfo.getEmail();
        String sub = appleInfo.getSub();

        if(email == null || sub == null)
            throw new ExceptionHandler(INVALID_APPLE_ID_TOKEN);


        Optional<Member> findSub = memberRepository.findByAppleSub(sub);
        Optional<Member> findEmail = memberRepository.findByEmail(email);

        if(!findSub.equals(findEmail))
            throw new MemberHandler(UNCORRECTED_INFO);

        Member member = null;
        if(findSub.isPresent() && findEmail.isPresent()) {  // 재로그인
            member = findEmail.get();
            if(!member.getProvider().equals(MemberProvider.KAKAO))   // 이미 회원가입했지만 apple이 아닌 다른 소셜 로그인 사용
                throw new MemberHandler(MEMBER_NOT_FOUND_IN_APPLE);
        } else if(!findSub.isPresent() && !findEmail.isPresent()) {  // 회원가입이 안되어있는 경우 -> 에러 발생. 회원가입 해야 함
            throw new MemberHandler(MEMBER_NOT_FOUND);
        }

        // accessToken, refreshToken 발급
        if(member == null)
            throw new MemberHandler(MEMBER_NOT_FOUND);
        return createToken(member);
    }

    // 애플 로그인 (회원가입 해야하는 경우)
    @Transactional
    public LoginResponseDto appleSignUp(AppleSignUpRequestDto appleSignUpRequestDto) {
        // email, sub값 추출 후 db에서 해당 email값 그리고 sub값을 가진 유저가 있는지 find
        // 1. 추출한 email, sub 값이 null이면 -> 잘못된 apple token
        // 2. db에서 각각 find한 회원 id가 다르면 에러 (올바르지 않은 정보)
        // 3. db에 email, sub값 둘 다 있으면 재로그인 (혹시 provider가 다르다면 에러)
        // 4. db에 email, sub값 둘 다 없으면 회원가입
        // 탈퇴 처리는 추후에

        AppleInfo appleInfo = jwtService.getAppleAccountId(appleSignUpRequestDto.getIdentityToken());
        String email = appleInfo.getEmail();
        String sub = appleInfo.getSub();

        if(email == null || sub == null)
            throw new ExceptionHandler(INVALID_APPLE_ID_TOKEN);


        Optional<Member> findSub = memberRepository.findByAppleSub(sub);
        Optional<Member> findEmail = memberRepository.findByEmail(email);

        if(!findSub.equals(findEmail))
            throw new MemberHandler(UNCORRECTED_INFO);

        Member member = null;
        if(findSub.isPresent() && findEmail.isPresent()) {  // 이미 회원가입한 회원인 경우 -> 에러 발생
            throw new MemberHandler(ALREADY_MEMBER);
        } else if(!findSub.isPresent() && !findEmail.isPresent()) {
            member = memberRepository.save(
                    Member.builder()
                            .email(email)
                            .provider(MemberProvider.APPLE)
                            .appleSub(sub)
                            .refreshToken("")
                            .refreshTokenExpiresAt(LocalDateTime.now())
                            .build()
            );
            System.out.println("member id : " + member.getMemberId());
            System.out.println("member email : " + member.getEmail());
        }

        // accessToken, refreshToken 발급
        if(member == null)
            throw new MemberHandler(MEMBER_NOT_FOUND);
        return createToken(member);
    }

}
