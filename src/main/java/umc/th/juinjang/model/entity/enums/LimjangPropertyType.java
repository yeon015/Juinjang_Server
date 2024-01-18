package umc.th.juinjang.model.entity.enums;

import java.util.Arrays;

// 매물유형
public enum LimjangPropertyType {
  APARTMENT(0), // 아파트
  VILLA(1),// 빌라
  OFFICE_TEL(2), // 오피스텔
  DETACHED_HOUSE(3); // 단독주택

  private final int value;

  LimjangPropertyType(int value) {
    this.value = value;
  }

  // 숫자 리턴
  public int getValue() {
    return value;
  }

  public static LimjangPropertyType find(int inputValue) {
    return Arrays.stream(LimjangPropertyType.values())
        .filter(it -> it.value == inputValue)
        .findAny()
        .orElseThrow(() -> new IllegalArgumentException("해당 입력값은 잘못되었습니다.."));
  }
}
