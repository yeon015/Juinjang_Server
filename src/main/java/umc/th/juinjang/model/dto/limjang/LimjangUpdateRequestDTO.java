package umc.th.juinjang.model.dto.limjang;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import umc.th.juinjang.validation.annotation.VaildPriceListSize;

public class LimjangUpdateRequestDTO {

  @Getter
  public static class UpdateDto {

    @NotNull
    private Long limjangId;

    @NotBlank
    private String address;

    @NotBlank
    private String addressDetail;

    @NotBlank
    private String nickname;

    @NotNull
    private Integer priceType;

    @VaildPriceListSize(minSize = 1, maxSize = 2)
    private List<String> priceList;

  }

  @Getter
  public static class LimjangMemoDto{
    private String memo;
  }
}
