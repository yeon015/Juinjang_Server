package umc.th.juinjang.model.dto.auth.apple;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class AppleTokenRequest {
    private String client_id;
    private String client_secret;
    private String code; //authorization code
    private String grant_type;
}
