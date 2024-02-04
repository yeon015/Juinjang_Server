package umc.th.juinjang.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponseDto.nicknameDto patchNickname(Long memberId, MemberRequestDto memberRequestDto) {
        // Member 받아오면 해당 member의 nickname 변경
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberHandler(MEMBER_NOT_FOUND));

        member.updateNickname(memberRequestDto.getNickname());

        return new MemberResponseDto.nicknameDto(member.getNickname());
    }
}
