package umc.th.juinjang.utils;

import java.util.List;
import umc.th.juinjang.model.dto.limjang.LimjangPostRequestDTO.PostDto;
import umc.th.juinjang.model.entity.LimjangPrice;

public class LimjangUtil {
  public static LimjangPrice determineLimjangPrice(
      List<String> priceList, Integer purpose, Integer priceType
      ){

    if (purpose == 0){ // 부동산 투자 목적 -> 실거래가
      return LimjangPrice.builder().marketPrice(priceList.get(0)).build();
    } else if (purpose == 1){ // 직접 거래 목적
      switch (priceType){
        case 0 : // 매매
          return LimjangPrice.builder().sellingPrice(priceList.get(0)).build();
        case 1 :// 전세
          return LimjangPrice.builder().pullRent(priceList.get(0)).build();
        case 2 : // 월세 : 0, 보증금 : 1 이 경우 배열 길이는 무조건 2여야만 함.
          return LimjangPrice.builder().depositPrice(priceList.get(0))
              .monthlyRent(priceList.get(1)).build();
        case 3 :
          return LimjangPrice.builder().marketPrice(priceList.get(0)).build();
      }
    }
    return null;
  }
}
