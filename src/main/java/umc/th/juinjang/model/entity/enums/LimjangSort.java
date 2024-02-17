package umc.th.juinjang.model.entity.enums;

public enum LimjangSort {
  UPDATED("UPDATED"),
  CREATED("CREATED"),
  STAR("STAR");

  private final String value;

  LimjangSort(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return value;
  }
}
