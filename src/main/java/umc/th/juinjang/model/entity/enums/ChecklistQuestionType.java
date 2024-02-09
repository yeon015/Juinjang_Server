package umc.th.juinjang.model.entity.enums;

import umc.th.juinjang.apiPayload.code.status.ErrorStatus;
import umc.th.juinjang.apiPayload.exception.handler.ChecklistHandler;

import java.util.Arrays;

public enum ChecklistQuestionType {

  // 점수
  SCORE(0),
  // 드롭다운
  DROPDOWN(1),
  //텍스트필드
  TEXT_FIELD(2),
  //캘린더
  CALENDER(3);
  private final int value;

  ChecklistQuestionType(int value) {
    this.value = value;
  }

  // 숫자 리턴
  public int getValue() {
    return value;
  }

  public static ChecklistQuestionType find(int inputValue) {
    return Arrays.stream(ChecklistQuestionType.values())
            .filter(it -> it.value == inputValue)
            .findAny()
            .orElseThrow(() -> new ChecklistHandler(ErrorStatus.CHECKLIST_TYPE_ERROR));
  }
}
