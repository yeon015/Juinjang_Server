package umc.th.juinjang.model.entity.enums;


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
}
