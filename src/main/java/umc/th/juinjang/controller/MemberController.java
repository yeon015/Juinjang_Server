package umc.th.juinjang.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.th.juinjang.apiPayload.ApiResponse;
import umc.th.juinjang.apiPayload.ExceptionHandler;
import umc.th.juinjang.apiPayload.code.status.ErrorStatus;
import umc.th.juinjang.apiPayload.exception.handler.MemberHandler;
import umc.th.juinjang.model.dto.member.MemberAgreeVersionPostRequest;
import umc.th.juinjang.model.dto.member.MemberRequestDto;
import umc.th.juinjang.model.dto.member.MemberResponseDto;
import umc.th.juinjang.model.entity.Member;
import umc.th.juinjang.service.MemberService.MemberService;

import static umc.th.juinjang.apiPayload.code.status.ErrorStatus.NICKNAME_EMPTY;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class MemberController {

    private final MemberService memberService;

    @CrossOrigin
    @Operation(summary = "닉네임 설정")
    @PatchMapping("/nickname")
    public ApiResponse<MemberResponseDto.nicknameDto> patchNickname (@AuthenticationPrincipal Member member, @RequestBody MemberRequestDto memberRequestDto) {
        if(!memberRequestDto.getNickname().isEmpty()) {
            MemberResponseDto.nicknameDto result = memberService.patchNickname(member, memberRequestDto);
            return ApiResponse.onSuccess(result);
        } else
            throw new ExceptionHandler(NICKNAME_EMPTY);
    }

    @CrossOrigin
    @Operation(summary = "프로필 조회")
    @GetMapping("/profile")
    public ApiResponse<MemberResponseDto.profileDto> getProfile (@AuthenticationPrincipal Member member) {
        MemberResponseDto.profileDto result = memberService.getProfile(member);
        return ApiResponse.onSuccess(result);
    }

    @CrossOrigin
    @Operation(summary = "프로필 이미지 수정")
    @PatchMapping("/profile/image")
    public ApiResponse<MemberResponseDto.profileDto> getProfile (@AuthenticationPrincipal Member member, @RequestPart MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty())
            throw new ExceptionHandler(ErrorStatus.IMAGE_EMPTY);
        MemberResponseDto.profileDto result = memberService.updateProfileImage(member, multipartFile);
        return ApiResponse.onSuccess(result);
    }

    @Operation(summary = "약관 동의 버전 전송")
    @PostMapping ("/members/terms")
    public ApiResponse<Void> createMemberAgreeVersion(@AuthenticationPrincipal Member member, @RequestBody @Valid MemberAgreeVersionPostRequest memberAgreeVersionPostRequest) {
        memberService.createMemberAgreeVersion(member, memberAgreeVersionPostRequest);
        return ApiResponse.onSuccess(null);
    }
}
