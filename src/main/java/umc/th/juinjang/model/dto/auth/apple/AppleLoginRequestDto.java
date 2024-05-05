package umc.th.juinjang.model.dto.auth.apple;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppleLoginRequestDto {

    private String identityToken;
}
