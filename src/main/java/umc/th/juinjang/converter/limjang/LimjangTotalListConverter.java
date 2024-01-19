package umc.th.juinjang.converter.limjang;

import static umc.th.juinjang.utils.LimjangUtil.determineLimjangPrice;
import static umc.th.juinjang.utils.LimjangUtil.makePriceList;

import java.util.List;
import umc.th.juinjang.model.dto.limjang.LimjangTotalListResponseDTO;
import umc.th.juinjang.model.entity.Image;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.LimjangPrice;
import umc.th.juinjang.model.entity.Report;
import umc.th.juinjang.model.entity.enums.LimjangPriceType;


public class LimjangTotalListConverter {

  private static LimjangPriceType lim;
  LimjangTotalListResponseDTO.ListDto
  public static LimjangTotalListResponseDTO.TotalListDto toLimjangTotalList(
      List<LimjangTotalListResponseDTO.ListDto> scrapedList,
      List<LimjangTotalListResponseDTO.ListDto> notScrapedList
      ) {

    return LimjangTotalListResponseDTO.TotalListDto.builder()
        .scrapedList(scrapedList)
        .notScrapedList(notScrapedList)
        .build();
  }

  public static LimjangTotalListResponseDTO.ListDto toLimjangList(
      Limjang limjang, LimjangPrice limjangPrice) {
    
    List<String> urlList = limjang.getImageList().stream()
        .map(Image::getImageUrl)
        .toList();

    Integer purposeType = limjang.getPurpose().getValue();
    Integer priceType = limjang.getPriceType().getValue();
    List<String> priceList = makePriceList(priceType, purposeType,limjangPrice);

    determineLimjangPrice(priceList, purposeType, priceType);

    return LimjangTotalListResponseDTO.ListDto.builder()
        .limjangId(limjang.getLimjangId())
        .images(urlList)
        .nickname(limjang.getNickname())
        .priceType(priceType)
        .priceList(priceList)
        .totalAverage(limjang.getReport().getTotalRate())
        .address(limjang.getAddress())
        .createdAt(limjang.getCreatedAt())
        .updatedAt(limjang.getUpdatedAt())
        .build();
  }
}
