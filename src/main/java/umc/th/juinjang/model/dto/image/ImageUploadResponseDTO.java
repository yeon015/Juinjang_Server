package umc.th.juinjang.model.dto.image;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ImageUploadResponseDTO {
  @Builder
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ImagesDTO {
    List<String> images;
  }
}
