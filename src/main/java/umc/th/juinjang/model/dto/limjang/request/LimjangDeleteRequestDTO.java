package umc.th.juinjang.model.dto.limjang.request;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Getter;

public class LimjangDeleteRequestDTO {

  @Getter
  public static class DeleteDto {

    @NotEmpty List<Long> limjangIdList;
  }
}
