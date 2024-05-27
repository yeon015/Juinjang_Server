package umc.th.juinjang.model.dto.auth.kakao;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoLoginRequestDto {

    @NotEmpty
    private String email;
    private String kakaoNickname;
}
