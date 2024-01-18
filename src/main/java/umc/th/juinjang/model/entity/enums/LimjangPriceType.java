package umc.th.juinjang.model.entity.enums;

import java.util.Arrays;

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
        .orElseThrow(() -> new IllegalArgumentException("해당 입력값은 잘못되었습니다.."));
  }
}
