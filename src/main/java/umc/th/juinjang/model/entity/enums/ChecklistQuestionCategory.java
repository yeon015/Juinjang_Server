package umc.th.juinjang.model.entity.enums;


import umc.th.juinjang.apiPayload.code.status.ErrorStatus;
import umc.th.juinjang.apiPayload.exception.handler.ChecklistHandler;
import umc.th.juinjang.apiPayload.exception.handler.LimjangHandler;

import java.util.Arrays;

public enum ChecklistQuestionCategory {
  DEADLINE(0), //기한
  LOCATION_CONDITION(1), // 입지여건
  PUBLIC_SPACE(2), // 공용공간
  INDOOR(3); //실내

  private final int value;

  ChecklistQuestionCategory(int value) {
    this.value = value;
  }

  // 숫자 리턴
  public int getValue() {
    return value;
  }

  public static ChecklistQuestionCategory find(int inputValue) {
    return Arrays.stream(ChecklistQuestionCategory.values())
            .filter(it -> it.value == inputValue)
            .findAny()
            .orElseThrow(() -> new ChecklistHandler(ErrorStatus.CHECKLIST_TYPE_ERROR));
  }
}
