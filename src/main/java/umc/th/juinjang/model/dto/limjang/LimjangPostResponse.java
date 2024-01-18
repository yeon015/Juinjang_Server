package umc.th.juinjang.model.dto.limjang;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class LimjangPostResponse {

  @Builder
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class PostDTO {
    Long limjangId;
  }

  @Builder
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class PostExceptionDTO {
    Integer flag;
  }

}
