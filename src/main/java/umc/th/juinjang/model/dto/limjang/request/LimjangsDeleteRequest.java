package umc.th.juinjang.model.dto.limjang.request;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record LimjangsDeleteRequest(
    @NotEmpty List<Long> limjangIdList
) {
}