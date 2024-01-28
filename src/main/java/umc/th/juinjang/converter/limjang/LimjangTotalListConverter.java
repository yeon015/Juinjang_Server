package umc.th.juinjang.converter.limjang;

import static umc.th.juinjang.utils.LimjangUtil.determineLimjangPrice;
import static umc.th.juinjang.utils.LimjangUtil.makePriceList;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import umc.th.juinjang.model.dto.limjang.LimjangTotalListResponseDTO;
import umc.th.juinjang.model.entity.Image;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.LimjangPrice;
import umc.th.juinjang.model.entity.Report;
import umc.th.juinjang.model.entity.enums.LimjangPriceType;


public class LimjangTotalListConverter {

  public static LimjangTotalListResponseDTO.TotalListDto toLimjangTotalList(
      List<Limjang> limjangList
      ) {

    List<LimjangTotalListResponseDTO.ListDto> scrapedList = limjangList.stream()
        .filter(limjang -> limjang.getScrap() != null)
        .map(limjang -> LimjangTotalListConverter.toLimjangList(limjang, limjang.getPriceId(),3))
        .toList();

    // 스크랩 안 된 리스트
    List<LimjangTotalListResponseDTO.ListDto> unScrapedList = limjangList.stream()
        .filter(limjang -> limjang.getScrap() == null)
        .map(limjang -> LimjangTotalListConverter.toLimjangList(limjang, limjang.getPriceId(), 1))
        .toList();


    return LimjangTotalListResponseDTO.TotalListDto.builder()
        .scrapedList(scrapedList)
        .notScrapedList(unScrapedList)
        .build();
  }

  public static LimjangTotalListResponseDTO.ListDto toLimjangList(
      Limjang limjang, LimjangPrice limjangPrice, int limitImageListSize) {
    
    List<String> urlList = limjang.getImageList().stream()
        .sorted(Comparator.comparing(Image::getCreatedAt)) // createdAt 기준으로 정렬
        .map(Image::getImageUrl)
        .limit(limitImageListSize)
        .toList();

    Integer purposeType = limjang.getPurpose().getValue();
    Integer priceType = limjang.getPriceType().getValue();
    List<String> priceList = makePriceList(priceType, purposeType,limjangPrice);

    Boolean isScraped = true;

    if (limjang.getScrap() == null){
      isScraped = false;
    }

    return LimjangTotalListResponseDTO.ListDto.builder()
        .limjangId(limjang.getLimjangId())
        .images(urlList)
        .nickname(limjang.getNickname())
        .isScraped(isScraped)
        .purposeCode(limjang.getPurpose().getValue())
        .priceType(priceType)
        .priceList(priceList)
        .totalAverage( Optional.ofNullable(limjang.getReport())
            .flatMap(report -> Optional.ofNullable(report.getTotalRate())
                .map(Object::toString))
            .orElse(null))
        .address(limjang.getAddress())
        .createdAt(limjang.getCreatedAt())
        .updatedAt(limjang.getUpdatedAt())
        .build();
  }
}
