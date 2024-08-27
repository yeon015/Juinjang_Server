package umc.th.juinjang.converter.limjang;

import static umc.th.juinjang.service.LimjangService.LimjangPriceBridge.makePriceListVersion2;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import umc.th.juinjang.model.dto.limjang.response.LimjangsGetResponse;
import umc.th.juinjang.model.dto.limjang.response.LimjangsGetResponse.LimjangsResponse;
import umc.th.juinjang.model.entity.Image;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.LimjangPrice;

public class LimjangsGetResponseConverter {

  public static LimjangsGetResponse.LimjangsResponse convertToLimjangsResponse(Limjang limjang, LimjangPrice limjangPrice) {
    return LimjangsResponse.builder()
        .limjangId(limjang.getLimjangId())
        .images(getUrlListByScrap(limjang))
        .nickname(limjang.getNickname())
        .isScraped(limjang.getScrap() != null)
        .purposeCode(limjang.getPurpose().getValue())
        .priceType(limjang.getPriceType().getValue())
        .priceList(makePriceListVersion2(limjang.getPriceType(), limjang.getPurpose(), limjangPrice))
        .totalAverage(Optional.ofNullable(limjang.getReport())
            .flatMap(report -> Optional.ofNullable(report.getTotalRate())
                .map(Object::toString))
            .orElse(null))
        .address(limjang.getAddress())
        .build();
  }

  private static List<String> getUrlListByScrap(Limjang limjang) {
    return limjang.getImageList().stream()
        .sorted(Comparator.comparing(Image::getCreatedAt))
        .map(Image::getImageUrl)
        .limit(limjang.getScrap() == null ? 1 : 3)
        .toList();
  }
}