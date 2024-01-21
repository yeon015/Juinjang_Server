package umc.th.juinjang.model.entity.enums;

public enum ChecklistQuestionType {

  // 점수식 체크리스트
  INT(0),
  // 점수를 제외한 나머지 체크리스트
  STR(1),
  DATE(2);
  private final int value;

  ChecklistQuestionType(int value) {
    this.value = value;
  }

  // 숫자 리턴
  public int getValue() {
    return value;
  }
}
