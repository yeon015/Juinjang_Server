package umc.th.juinjang.model.entity.enums;

import java.util.Arrays;
import umc.th.juinjang.apiPayload.code.status.ErrorStatus;
import umc.th.juinjang.apiPayload.exception.handler.LimjangHandler;

public enum LimjangPriceType {
  SALE(0), // 매매
  MONTHLY_RENT(1), //월세
  PULL_RENT(2), // 전세
  MARKET_PRICE(3); // 실거래가


  private final int value;

  LimjangPriceType(int value) {
    this.value = value;
  }

  // 숫자 리턴
  public int getValue() {
    return value;
  }

  public static LimjangPriceType find(int inputValue) {
    return Arrays.stream(LimjangPriceType.values())
        .filter(it -> it.value == inputValue)
        .findAny()
        .orElseThrow(() -> new LimjangHandler(ErrorStatus.LIMJANG_POST_TYPE_ERROR));
  }
}
