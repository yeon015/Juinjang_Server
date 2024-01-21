package umc.th.juinjang.model.entity.enums;

public enum ChecklistQuestionVersion {

  // 원룸용 체크리스트
  ONE_ROOM(0),
  // 임장용 체크리스트
  LIMJANG(1);

  private final int value;

  ChecklistQuestionVersion(int value) {
    this.value = value;
  }

  // 숫자 리턴
  public int getValue() {
    return value;
  }
}
