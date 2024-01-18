package umc.th.juinjang.model.dto.limjang;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;

public class LimjangPostRequestDTO {

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
