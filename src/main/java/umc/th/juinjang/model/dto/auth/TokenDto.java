package umc.th.juinjang.model.dto.auth;

import lombok.Getter;

@Getter
public class TokenDto {

    private final Long memberId;

    public TokenDto(Long memberId) {
        this.memberId = memberId;
    }

}
