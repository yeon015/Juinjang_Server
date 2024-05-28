package umc.th.juinjang.service.LimjangService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.th.juinjang.apiPayload.code.status.ErrorStatus;
import umc.th.juinjang.apiPayload.exception.handler.LimjangHandler;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.repository.limjang.LimjangRepository;

@Component
@RequiredArgsConstructor
public class LimjangRetriever {

  private final LimjangRepository limjangRepository;

  public Limjang findById(long limjangId) {

    return limjangRepository.findById(limjangId)
        .orElseThrow(() -> new LimjangHandler(ErrorStatus.LIMJANG_NOTFOUND_ERROR));
  }
}
