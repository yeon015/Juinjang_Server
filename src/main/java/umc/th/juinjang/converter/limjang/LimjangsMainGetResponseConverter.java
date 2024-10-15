package umc.th.juinjang.converter.limjang;

import static umc.th.juinjang.service.LimjangService.LimjangPriceBridge.getPriceToString;

import java.util.Optional;
import umc.th.juinjang.model.dto.limjang.response.LimjangsMainGetResponse.LimjangMainResponse;
import umc.th.juinjang.model.entity.Image;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.Report;

public class LimjangsMainGetResponseConverter {

  public static LimjangMainResponse toLimjangMainResponse(final Limjang limjang) {
    return new LimjangMainResponse(
        limjang.getLimjangId(),
        limjang.getImageList().stream().findFirst().map(Image::getImageUrl).orElse(null),
        limjang.getNickname(), getPriceToString(limjang),
        getTotalAverageOrElse(limjang),
        limjang.getAddress());
  }

  private static String getTotalAverageOrElse(final Limjang limjang) {
    return Optional.ofNullable(limjang.getReport()).map(Report::getTotalRate).map(Object::toString).orElse(null);
  }
}