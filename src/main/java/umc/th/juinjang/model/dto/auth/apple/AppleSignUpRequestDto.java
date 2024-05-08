package umc.th.juinjang.model.dto.auth.apple;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppleSignUpRequestDto {

    @NotEmpty
    private String identityToken;
    @NotEmpty
    private String nickname;
}
