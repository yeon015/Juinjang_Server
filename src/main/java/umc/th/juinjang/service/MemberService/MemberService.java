package umc.th.juinjang.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.th.juinjang.apiPayload.exception.handler.MemberHandler;
import umc.th.juinjang.model.dto.member.MemberRequestDto;
import umc.th.juinjang.model.dto.member.MemberResponseDto;
import umc.th.juinjang.model.entity.Member;
import umc.th.juinjang.repository.limjang.MemberRepository;

import static umc.th.juinjang.apiPayload.code.status.ErrorStatus.MEMBER_NOT_FOUND;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberResponseDto.nicknameDto patchNickname(Member member, MemberRequestDto memberRequestDto) {
        // Member 받아오면 해당 member의 nickname 변경
        member.updateNickname(memberRequestDto.getNickname());
        memberRepository.save(member);  // 변수 없이 member 그대로 저장

        return new MemberResponseDto.nicknameDto(member.getNickname());
    }

    public MemberResponseDto.profileDto getProfile(Member member) {
        String provider = member.getProvider().toString();
        return new MemberResponseDto.profileDto(member.getNickname(), member.getEmail(), provider);
    }
}
