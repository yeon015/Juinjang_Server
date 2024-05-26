package umc.th.juinjang.service.ScrapService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.th.juinjang.apiPayload.code.status.ErrorStatus;
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
  private final LimjangRepository limjangRepository;
  private final LimjangRetriever limjangRetriever;

  @Override
  @Transactional
  public ScrapActionType actionScrap(Long limjangId) {

    Limjang limjang = limjangRepository.findById(limjangId)
        .orElseThrow(() -> new ScrapHandler(ErrorStatus._SCRAP_NOT_FOUND));

    Scrap findScrap= scrapRepository.findScrapByLimjangId(limjang);

    if (findScrap == null){
      Scrap newScrap = Scrap.builder().limjangId(limjang).build();
      try{
        limjang.addScrap(newScrap);
        limjangRepository.save(limjang);
        return ScrapActionType.SCRAP;
      }catch (IllegalArgumentException e) {
        log.warn("IllegalArgumentException");
        throw new ScrapHandler(ErrorStatus._SCRAP_SCRAP_FAILD);
      }
    } else { // 스크랩 되어있었음 -> DB에서 없애기
      log.warn(findScrap.getScrapId()+"DB에서 없앨것");

      try {
        limjang.removeScrap();
        scrapRepository.deleteById(findScrap.getScrapId());
        return ScrapActionType.UNSCRAP;

      } catch (Exception e) {
        throw new ScrapHandler(ErrorStatus._SCRAP_UNSCRAP_FAILD);
      }
    }
  }

  @Transactional
  @Override
  public void createScrap(long limjangId) {

    Limjang limjang = limjangRetriever.findById(limjangId); // 임장 ID 찾기

    Scrap newScrap = Scrap.builder().limjangId(limjang).build();

    try {
      scrapRepository.save(newScrap);
      System.out.println("--스크랩 완료 in service---");
    } catch (IllegalArgumentException e) {
      log.warn("IllegalArgumentException");
      throw new ScrapHandler(ErrorStatus._SCRAP_SCRAP_FAILD);
    }
  }
}
