package umc.th.juinjang.model.dto.auth.apple;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class AppleInfo {

    private String email;
    private String sub;

    @Builder
    public AppleInfo(String email, String sub) {
        this.email = email;
        this.sub = sub;
    }
}
