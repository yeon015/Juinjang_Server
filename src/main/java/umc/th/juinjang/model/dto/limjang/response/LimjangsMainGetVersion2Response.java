package umc.th.juinjang.model.dto.limjang.response;

import static umc.th.juinjang.service.LimjangService.LimjangPriceBridge.getPriceToString;

import java.util.List;
import lombok.Builder;
import umc.th.juinjang.model.entity.Limjang;

public record LimjangsMainGetVersion2Response(List<LimjangMainVersion2Response> recentUpdatedList) {
 @Builder
  record LimjangMainVersion2Response(
      long limjangId,
      int priceType,
      String image,
      String nickname,
      String price,
      String totalAverage,
      String address) {

   static LimjangMainVersion2Response of(Limjang limjang) {
     return LimjangMainVersion2Response.builder()
         .limjangId(limjang.getLimjangId())
         .priceType(limjang.getPriceType().getValue())
         .image(limjang.getDefaultImage())
         .nickname(limjang.getNickname())
         .price(getPriceToString(limjang))
         .totalAverage(limjang.getReport() == null ? null : limjang.getReport().getTotalRate().toString())
         .address(limjang.getAddress())
         .build();
   }
 }

  public static LimjangsMainGetVersion2Response of(List<Limjang> limjangList) {
    return new LimjangsMainGetVersion2Response(limjangList.stream().map(LimjangMainVersion2Response::of).toList());
  }
}
