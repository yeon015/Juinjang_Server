package umc.th.juinjang.model.dto.limjang.response;

import java.time.LocalDateTime;
import umc.th.juinjang.model.entity.Limjang;

public record LimjangPostResponse(Long limjangId, LocalDateTime createdAt) {
  public static LimjangPostResponse of(Limjang limjang) {
    return new LimjangPostResponse(limjang.getLimjangId(), limjang.getCreatedAt());
  }
}