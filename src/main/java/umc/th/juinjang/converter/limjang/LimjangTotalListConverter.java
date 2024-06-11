package umc.th.juinjang.converter.limjang;

import static umc.th.juinjang.service.LimjangService.LimjangPriceBridge.makePriceList;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import umc.th.juinjang.model.dto.limjang.LimjangTotalListResponseDTO;
import umc.th.juinjang.model.dto.limjang.LimjangTotalListResponseDTO.LimjangListDto;
import umc.th.juinjang.model.entity.Image;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.LimjangPrice;


public class LimjangTotalListConverter {

  public static LimjangTotalListResponseDTO.TotalListDto toLimjangTotalList(
      List<Limjang> limjangList
      ) {

//    List<LimjangListDto> scrapedList = limjangList.stream()
//        .filter(limjang -> limjang.getScrap() != null)
//        .map(limjang -> LimjangTotalListConverter.toLimjangList(limjang, limjang.getPriceId(),3))
//        .toList();
//
//    // 스크랩 안 된 리스트
//    List<LimjangListDto> unScrapedList = limjangList.stream()
//        .filter(limjang -> limjang.getScrap() == null)
//        .map(limjang -> LimjangTotalListConverter.toLimjangList(limjang, limjang.getPriceId(), 1))
//        .toList();

    List<LimjangListDto> limjangListDto = limjangList.stream()
        .map(limjang -> {
          if (limjang.getScrap() == null ) {
            return LimjangTotalListConverter.toLimjangList(limjang, limjang.getLimjangPrice(), 1);
          } else {
            return LimjangTotalListConverter.toLimjangList(limjang, limjang.getLimjangPrice(), 3);
          }
        })
        .toList();

    return LimjangTotalListResponseDTO.TotalListDto.builder()
        .limjangList(limjangListDto)
        .build();
  }

  public static LimjangListDto toLimjangList(
      Limjang limjang, LimjangPrice limjangPrice, int limitImageListSize) {

    System.out.println("---------------");
    System.out.println("toLimjangList입니다. 이미지를 찾습니다... ");
    List<String> urlList = limjang.getImageList().stream()
        .sorted(Comparator.comparing(Image::getCreatedAt)) // image를 createdAt 기준으로 정렬
        .map(Image::getImageUrl)
        .limit(limitImageListSize)
        .toList();

    System.out.println("---------------");
    System.out.println("toLimjangList입니다. Limjangprice 찾습니다... ");
    Integer purposeType = limjang.getPurpose().getValue();
    Integer priceType = limjang.getPriceType().getValue();
    List<String> priceList = makePriceList(priceType, purposeType,limjangPrice);

    Boolean isScraped = true;

    if (limjang.getScrap() == null){
      isScraped = false;
    }

    return LimjangListDto.builder()
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
//        .createdAt(limjang.getCreatedAt())
//        .updatedAt(limjang.getUpdatedAt())
        .build();
  }
}
