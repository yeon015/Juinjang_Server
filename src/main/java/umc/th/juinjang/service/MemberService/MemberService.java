package umc.th.juinjang.service.MemberService;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import umc.th.juinjang.apiPayload.ExceptionHandler;
import umc.th.juinjang.apiPayload.code.status.ErrorStatus;
import umc.th.juinjang.apiPayload.exception.handler.MemberHandler;
import umc.th.juinjang.converter.record.RecordConverter;
import umc.th.juinjang.model.dto.member.MemberRequestDto;
import umc.th.juinjang.model.dto.member.MemberResponseDto;
import umc.th.juinjang.model.entity.Member;
import umc.th.juinjang.model.entity.Record;
import umc.th.juinjang.repository.limjang.MemberRepository;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static umc.th.juinjang.apiPayload.code.status.ErrorStatus.ALREADY_NICKNAME;
import static umc.th.juinjang.apiPayload.code.status.ErrorStatus.MEMBER_NOT_FOUND;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.uploadPath}")
    private String defaultUrl;

    @Autowired
    private final AmazonS3Client amazonS3Client;
    private final MemberRepository memberRepository;

    // 닉네임 수정
    public MemberResponseDto.nicknameDto patchNickname(Member member, MemberRequestDto memberRequestDto) {
        // Member 받아오면 해당 member의 nickname 변경
        member.updateNickname(memberRequestDto.getNickname());
        memberRepository.save(member);  // 변수 없이 member 그대로 저장

        return new MemberResponseDto.nicknameDto(member.getNickname());
    }

    // 프로필 조회
    public MemberResponseDto.profileDto getProfile(Member member) {
        String provider = member.getProvider().toString();
        return new MemberResponseDto.profileDto(member.getNickname(), member.getEmail(), provider, member.getImageUrl());
    }

    // 프로필 이미지 수정
    public MemberResponseDto.profileDto updateProfileImage(Member member, MultipartFile multipartFile) {
        String newUrl = null;
        String fileUrl = member.getImageUrl();

        if(fileUrl != null) {
            String[] url = fileUrl.split("/");
            amazonS3Client.deleteObject(bucket, url[3]);
        }

        try {
            String originalFilename = multipartFile.getOriginalFilename();
            String newfileName = UUID.randomUUID() + "_" + originalFilename;

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(multipartFile.getSize());
            metadata.setContentType(multipartFile.getContentType());

            //S3에 저장
            amazonS3Client.putObject(bucket, "profile/" +newfileName, multipartFile.getInputStream(), metadata);
            newUrl = amazonS3Client.getUrl(bucket, "profile/" + newfileName).toString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch(AmazonServiceException e){
            e.printStackTrace();
        }
        
        if(newUrl == null)
            throw new ExceptionHandler(ErrorStatus.IMAGE_NOT_SAVE);

        member.updateImage(newUrl);
        memberRepository.save(member);

        return new MemberResponseDto.profileDto(member.getNickname(), member.getEmail(), member.getProvider().toString(), member.getImageUrl());
    }
}
