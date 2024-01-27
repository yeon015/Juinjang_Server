package umc.th.juinjang.model.dto.limjang;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class LimjangTotalListResponseDTO {
  @Builder
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class TotalListDto {

    private List<ListDto> scrapedList;
    private List<ListDto> notScrapedList;

  }

  @Builder
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ListDto {
    private Long limjangId;
    private List<String> images;
    private Integer purposeCode;
    private Boolean isScraped;
    private String nickname;
    private Integer priceType;
    private List<String> priceList;
    private String totalAverage;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
  }

}
