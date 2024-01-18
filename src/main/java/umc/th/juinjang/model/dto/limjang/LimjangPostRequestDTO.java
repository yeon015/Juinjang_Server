package umc.th.juinjang.model.dto.limjang;

import jakarta.validation.constraints.*;
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

    @NotEmpty
    private List<String> price;

    @NotBlank
    private String address;

    @NotBlank
    private String addressDetail;

    @NotBlank
    private String nickname;
  }
}
