package umc.th.juinjang.model.dto.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WithdrawReasonRequestDto {
    @NotEmpty
    List<String> withdrawReason;
}
