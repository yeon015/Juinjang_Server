package umc.th.juinjang.model.dto.limjang.response;

import java.util.List;
import lombok.Builder;
import umc.th.juinjang.converter.limjang.LimjangsGetResponseConverter;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.LimjangPrice;

public record LimjangsGetResponse(
    List<LimjangsResponse> limjangList
) {
  @Builder
  public record LimjangsResponse(
      long limjangId,
      List<String> images,
      int purposeCode,
      boolean isScraped,
      String nickname,
      int priceType,
      List<String> priceList,
      String totalAverage,
      String address) {
    public static LimjangsResponse of(Limjang limjang, LimjangPrice limjangPrice) {
      return LimjangsGetResponseConverter.convertToLimjangsResponse(limjang, limjangPrice);
    }
  }

  public static LimjangsGetResponse of(final List<Limjang> limjangList) {
    return new LimjangsGetResponse(limjangList.stream().map(limjang -> LimjangsResponse.of(limjang, limjang.getLimjangPrice())).toList());
  }
}
