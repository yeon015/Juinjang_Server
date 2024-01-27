package umc.th.juinjang.service.ScrapService;

import java.util.Optional;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.th.juinjang.apiPayload.code.status.ErrorStatus;
import umc.th.juinjang.apiPayload.exception.handler.MemberHandler;
import umc.th.juinjang.apiPayload.exception.handler.ScrapHandler;
import umc.th.juinjang.converter.limjang.LimjangPostConverter;
import umc.th.juinjang.model.dto.limjang.LimjangPostRequestDTO.PostDto;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.LimjangPrice;
import umc.th.juinjang.model.entity.Member;
import umc.th.juinjang.model.entity.Scrap;
import umc.th.juinjang.model.entity.enums.ScrapActionType;
import umc.th.juinjang.repository.limjang.LimjangRepository;
import umc.th.juinjang.repository.limjang.ScrapRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScrapCommandServiceImpl implements ScrapCommandService {

  private final ScrapRepository scrapRepository;
  private final LimjangRepository limjangRepository;

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

}
