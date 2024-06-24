package umc.th.juinjang.converter.limjang;

import static umc.th.juinjang.service.LimjangService.LimjangPriceBridge.makePriceList;
import static umc.th.juinjang.service.LimjangService.LimjangPriceBridge.makePriceListVersion2;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import umc.th.juinjang.model.dto.limjang.LimjangTotalListResponseDTO;
import umc.th.juinjang.model.dto.limjang.LimjangTotalListResponseDTO.LimjangListDto;
import umc.th.juinjang.model.entity.Image;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.LimjangPrice;
import umc.th.juinjang.model.entity.enums.LimjangPriceType;
import umc.th.juinjang.model.entity.enums.LimjangPurpose;


public class LimjangTotalListConverter {

  public static LimjangTotalListResponseDTO.TotalListDto toLimjangTotalList(
      List<Limjang> limjangList
      ) {

    List<LimjangListDto> limjangListDto = limjangList.stream()
        .map(limjang -> LimjangTotalListConverter.toLimjangList(limjang, limjang.getLimjangPrice()))
        .toList();

    return LimjangTotalListResponseDTO.TotalListDto.builder()
        .limjangList(limjangListDto)
        .build();
  }

  public static LimjangListDto toLimjangList(
      Limjang limjang, LimjangPrice limjangPrice) {

    int limitImageListSize = limjang.getScrap() == null ? 1 : 3;

    List<String> urlList = limjang.getImageList().stream()
        .sorted(Comparator.comparing(Image::getCreatedAt)) // image를 createdAt 기준으로 정렬
        .map(Image::getImageUrl)
        .limit(limitImageListSize)
        .toList();

    List<String> priceList = makePriceListVersion2(limjang.getPriceType(), limjang.getPurpose(), limjangPrice);

    return LimjangListDto.builder()
        .limjangId(limjang.getLimjangId())
        .images(urlList)
        .nickname(limjang.getNickname())
        .isScraped(limjang.getScrap() != null)
        .purposeCode(limjang.getPurpose().getValue())
        .priceType(limjang.getPriceType().getValue())
        .priceList(priceList)
        .totalAverage(Optional.ofNullable(limjang.getReport())
            .flatMap(report -> Optional.ofNullable(report.getTotalRate())
                .map(Object::toString))
            .orElse(null))
        .address(limjang.getAddress())
        .build();
  }
}
