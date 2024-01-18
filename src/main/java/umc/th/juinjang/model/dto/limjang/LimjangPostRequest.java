package umc.th.juinjang.model.dto.limjang;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

public class LimjangPostRequest {

  @Getter
  public static class PostDto {

    @NotNull
    private int purpose;

    @NotNull
    private int propertyType;

    @NotNull
    private int priceType;

    @NotNull
    private List<String> price;

    @NotNull
    private String address;

    @NotNull
    private String addressDetail;

    @NotNull
    private String nickname;
  }
}
