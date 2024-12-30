package umc.th.juinjang.service.ScrapService;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.th.juinjang.apiPayload.code.status.ErrorStatus;
import umc.th.juinjang.apiPayload.exception.handler.LimjangHandler;
import umc.th.juinjang.apiPayload.exception.handler.ScrapHandler;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.Member;
import umc.th.juinjang.model.entity.Scrap;
import umc.th.juinjang.repository.limjang.LimjangRepository;
import umc.th.juinjang.repository.limjang.ScrapRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScrapServiceImpl implements ScrapService {

  private final ScrapRepository scrapRepository;
  private final LimjangRepository limjangRepository;

  @Override
  @Transactional
  public void createScrap(final Member member, final long limjangId) {
    Limjang limjang = getLimjangByMemberAndId(member, limjangId);
    checkScrapAlreadyScraped(limjang);
    scrapRepository.save(Scrap.create(limjang));
  }

  @Override
  @Transactional
  public void deleteScrap(final Member member, final long limjangId) {
    Limjang limjang = getLimjangByMemberAndId(member, limjangId);
    Scrap scrap = checkScrapAlreadyUnScraped(findByLimjang(limjang));
    scrapRepository.deleteById(scrap.getScrapId());
  }

  private Limjang getLimjangByMemberAndId(final Member member, final long limjangId) {
    return limjangRepository.findLimjangByLimjangIdAndMemberIdAndDeletedIsFalse(limjangId, member)
        .orElseThrow(() -> new LimjangHandler(ErrorStatus.LIMJANG_NOTFOUND_ERROR));
  }

  private void checkScrapAlreadyScraped(final Limjang limjang) {
    if (findByLimjang(limjang).isPresent()) {
      throw new ScrapHandler(ErrorStatus._SCRAP_ALREADY_SCRAPED);
    }
  }

  private Optional<Scrap> findByLimjang(final Limjang limjang) {
    return scrapRepository.findByLimjangId(limjang);
  }

  private Scrap checkScrapAlreadyUnScraped(final Optional<Scrap> scrap) {
    return scrap.orElseThrow(() -> new ScrapHandler(ErrorStatus._SCRAP_ALREADY_UNSCRAPED));
  }
}
