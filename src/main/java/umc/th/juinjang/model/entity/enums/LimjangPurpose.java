package umc.th.juinjang.model.entity.enums;

import java.util.Arrays;
import org.hibernate.sql.ComparisonRestriction.Operator;

public enum LimjangPurpose {

  INVESTMENT(0),  // 투자 목적
  RESIDENTIAL_PURPOSE(1); // 거주 목적

  private final int value;

  LimjangPurpose(int value) {
    this.value = value;
  }

  // 숫자 리턴
  public int getValue() {
    return value;
  }

  public static LimjangPurpose find(int inputValue) {
    return Arrays.stream(LimjangPurpose.values())
        .filter(it -> it.value == inputValue)
        .findAny()
        .orElseThrow(() -> new IllegalArgumentException("해당 입력값은 잘못되었습니다.."));
  }

}
