package umc.th.juinjang.model.dto.limjang;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.th.juinjang.model.dto.limjang.LimjangTotalListResponseDTO.ListDto;

public class LimjangMainViewListResponsetDTO {
  @Builder
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class RecentUpdatedDto {

    private List<LimjangMainViewListResponsetDTO.ListDto> recentUpdatedList;

  }

  @Builder
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ListDto {
    private Long limjangId;
    private Optional<String> image;
    private String nickname;
    private String price;
    private String totalAverage;
    private String address;
  }
}
