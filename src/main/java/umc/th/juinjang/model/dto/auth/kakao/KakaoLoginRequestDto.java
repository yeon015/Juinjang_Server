package umc.th.juinjang.model.dto.auth.kakao;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoLoginRequestDto {

    private String email;
    private String nickname;
}
