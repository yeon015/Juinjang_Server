package umc.th.juinjang.model.dto.image;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Getter;

public class ImageDeleteRequestDTO {
  @Getter
  public static class DeleteDto {

    @NotEmpty List<Long> imageIdList;
  }
}
