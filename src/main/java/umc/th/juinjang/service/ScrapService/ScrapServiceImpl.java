package umc.th.juinjang.service.ScrapService;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.th.juinjang.apiPayload.code.status.ErrorStatus;
import umc.th.juinjang.apiPayload.exception.GeneralException;
import umc.th.juinjang.apiPayload.exception.handler.LimjangHandler;
import umc.th.juinjang.apiPayload.exception.handler.ScrapHandler;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.Scrap;
import umc.th.juinjang.model.entity.enums.ScrapActionType;
import umc.th.juinjang.repository.limjang.LimjangRepository;
import umc.th.juinjang.repository.limjang.ScrapRepository;
import umc.th.juinjang.service.LimjangService.LimjangRetriever;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScrapServiceImpl implements ScrapService {

  private final ScrapRepository scrapRepository;
  private final LimjangRetriever limjangRetriever;
  private final ScrapRetriever scrapRetriever;

  @Override
  @Transactional
  public void createScrap(long limjangId) {

    Limjang findLimjang = limjangRetriever.findById(limjangId); // 임장 ID 찾기
    Optional<Scrap> findScrap = scrapRetriever.findByLimjang(findLimjang);

    if (findScrap.isPresent()) {
      throw new ScrapHandler(ErrorStatus._SCRAP_ALREADY_SCRAPED);
    }

    Scrap newScrap = Scrap.builder().limjangId(findLimjang).build();
    scrapRepository.save(newScrap);
    System.out.println("--스크랩 완료 in service---");
  }

  @Override
  @Transactional
  public void deleteScrap(long limjangId) {

    Limjang findLimjang = limjangRetriever.findById(limjangId); // 임장 ID 찾기
    Optional<Scrap> findScrap = scrapRetriever.findByLimjang(findLimjang);

    if (findScrap.isEmpty()) {
      throw new ScrapHandler(ErrorStatus._SCRAP_ALREADY_UNSCRAPED);
    } else {
      System.out.println("---------"+findLimjang.getLimjangId()+"------");
      scrapRepository.deleteByScrapId(findScrap.get().getScrapId());
      System.out.println("--스크랩 삭제 in service---");
    }
  }
}
