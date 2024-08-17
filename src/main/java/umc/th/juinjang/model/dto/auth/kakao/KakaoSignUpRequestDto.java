package umc.th.juinjang.model.dto.auth.kakao;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoSignUpRequestDto {

    @NotEmpty
    private String email;
    private String kakaoNickname;
    @NotEmpty
    private String nickname;
    @NotNull
    private Long kakaoTargetId;
}
