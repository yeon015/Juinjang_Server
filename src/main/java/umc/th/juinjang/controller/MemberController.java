package umc.th.juinjang.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import umc.th.juinjang.apiPayload.ApiResponse;
import umc.th.juinjang.apiPayload.ExceptionHandler;
import umc.th.juinjang.apiPayload.exception.handler.MemberHandler;
import umc.th.juinjang.model.dto.member.MemberRequestDto;
import umc.th.juinjang.model.dto.member.MemberResponseDto;
import umc.th.juinjang.service.MemberService.MemberService;

import static umc.th.juinjang.apiPayload.code.status.ErrorStatus.NICKNAME_EMPTY;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class MemberController {

    private final MemberService memberService;

//    @CrossOrigin
//    @Operation(summary = "닉네임 설정")
//    @PatchMapping("/nickname")
//    public ApiResponse<MemberResponseDto.nicknameDto> patchNickname (@AuthenticationPrincipal UserDetails member, @RequestBody MemberRequestDto memberRequestDto) {
//        // Member 로 수정해야함
//        if(!memberRequestDto.getNickname().isEmpty()) {
//            MemberResponseDto.nicknameDto result = memberService.patchNickname(member, memberRequestDto); // member로 수정해야함
//            return ApiResponse.onSuccess(result);
//        } else
//            throw new ExceptionHandler(NICKNAME_EMPTY);
//    }

    @CrossOrigin
    @Operation(summary = "프로필 조회")
    @GetMapping("/profile")
    public ApiResponse<MemberResponseDto.profileDto> getProfile (@PathVariable(name="memberId") Long memberId) { // Member 로 수정해야함
        return null;
    }
}
