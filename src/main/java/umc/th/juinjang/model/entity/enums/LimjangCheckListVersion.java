package umc.th.juinjang.model.entity.enums;

import umc.th.juinjang.model.entity.Limjang;

public enum LimjangCheckListVersion {
  LIMJANG,
  NON_LIMJANG;

  public static LimjangCheckListVersion getByLimjangType(Limjang limjang) {
    LimjangPropertyType propertyType = limjang.getPropertyType();

    if (limjang.getPurpose() == LimjangPurpose.RESIDENTIAL_PURPOSE && (propertyType == LimjangPropertyType.VILLA || propertyType == LimjangPropertyType.OFFICE_TEL)) {
      return LimjangCheckListVersion.NON_LIMJANG;
    }
    return LimjangCheckListVersion.LIMJANG;
  }
}