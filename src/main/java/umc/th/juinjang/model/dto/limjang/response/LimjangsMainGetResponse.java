package umc.th.juinjang.model.dto.limjang.response;

import java.util.List;
import umc.th.juinjang.converter.limjang.LimjangsMainGetResponseConverter;
import umc.th.juinjang.model.entity.Limjang;

public record LimjangsMainGetResponse(
    List<LimjangMainResponse> recentUpdatedList
) {
  public record LimjangMainResponse(
      long limjangId,
      String image,
      String nickname,
      String price,
      String totalAverage,
      String address
  ) {
    public static LimjangMainResponse of(final Limjang limjang) {
      return LimjangsMainGetResponseConverter.toLimjangMainResponse(limjang);
    }
  }

  public static LimjangsMainGetResponse of(final List<Limjang> limjangs) {
    return new LimjangsMainGetResponse(limjangs.stream().map(LimjangMainResponse::of).toList());
  }
}