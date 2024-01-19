package umc.th.juinjang.model.dto.limjang;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.th.juinjang.validation.annotation.VaildPriceListSize;

public class LimjangTotalListResponseDTO {
  @Builder
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ListDto {

    private List<LimjangTotalList> scrapedList;
    private List<LimjangTotalList> notScrapedList;

  }

  public static class LimjangTotalList{
    private Long limjangId;
    private List<String> images;
    private String nickname;
    private Integer priceType;
    private List<String> priceList;
    private float totalAverage;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

  }

}
