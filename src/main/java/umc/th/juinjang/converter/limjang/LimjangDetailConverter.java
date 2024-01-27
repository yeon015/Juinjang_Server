package umc.th.juinjang.converter.limjang;

import static umc.th.juinjang.utils.LimjangUtil.makePriceList;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import umc.th.juinjang.model.dto.limjang.LimjangDetailResponseDTO;
import umc.th.juinjang.model.dto.limjang.LimjangTotalListResponseDTO;
import umc.th.juinjang.model.entity.Image;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.LimjangPrice;

public class LimjangDetailConverter {

  public static LimjangDetailResponseDTO.DetailDto toDetail(
      Limjang limjang, LimjangPrice limjangPrice) {

    List<String> urlList = limjang.getImageList().stream()
        .sorted(Comparator.comparing(Image::getCreatedAt)) // createdAt 기준으로 정렬
        .map(Image::getImageUrl)
        .limit(3)
        .toList();

    Integer purposeType = limjang.getPurpose().getValue();
    Integer priceType = limjang.getPriceType().getValue();
    List<String> priceList = makePriceList(priceType, purposeType,limjangPrice);

    return LimjangDetailResponseDTO.DetailDto.builder()
        .limjangId(limjang.getLimjangId())
        .images(urlList)
        .nickname(limjang.getNickname())
        .purposeCode(limjang.getPurpose().getValue())
        .priceType(priceType)
        .priceList(priceList)
        .address(limjang.getAddress())
        .addressDetail(limjang.getAddressDetail())
        .createdAt(limjang.getCreatedAt())
        .updatedAt(limjang.getUpdatedAt())
        .build();
  }
}
