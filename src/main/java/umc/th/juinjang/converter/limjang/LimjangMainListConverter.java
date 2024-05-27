package umc.th.juinjang.converter.limjang;

import static umc.th.juinjang.utils.LimjangUtil.makePriceList;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import umc.th.juinjang.model.dto.limjang.LimjangMainViewListResponsetDTO;
import umc.th.juinjang.model.entity.Image;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.LimjangPrice;

public class LimjangMainListConverter {
  public static LimjangMainViewListResponsetDTO.RecentUpdatedDto toLimjangMainList(
      List<LimjangMainViewListResponsetDTO.ListDto> recentUpdatedList
  ) {

    return LimjangMainViewListResponsetDTO.RecentUpdatedDto.builder()
        .recentUpdatedList(recentUpdatedList)
        .build();
  }

  public static LimjangMainViewListResponsetDTO.ListDto toLimjangList(
      Limjang limjang, LimjangPrice limjangPrice) {

    Image imageList = limjang.getImageList().stream()
        .min(Comparator.comparing(Image::getCreatedAt)).orElse(null);

    Integer purposeType = limjang.getPurpose().getValue();
    Integer priceType = limjang.getPriceType().getValue();
    List<String> priceList = makePriceList(priceType, purposeType,limjangPrice);

    String price = (priceType == 2) ? priceList.get(1) : priceList.get(0);

    return LimjangMainViewListResponsetDTO.ListDto.builder()
        .limjangId(limjang.getLimjangId())
        .image(Optional.ofNullable(imageList).map(Image::getImageUrl))
        .nickname(limjang.getNickname())
        .price(price)
        .totalAverage( Optional.ofNullable(limjang.getReport())
            .flatMap(report -> Optional.ofNullable(report.getTotalRate())
                .map(Object::toString))
            .orElse(null))
        .address(limjang.getAddress())
        .build();
  }

}
