package umc.th.juinjang.service.ScrapService;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.Scrap;
import umc.th.juinjang.repository.limjang.ScrapRepository;

@Component
@RequiredArgsConstructor
public class ScrapRetriever {

  private final ScrapRepository scrapRepository;

  public Optional<Scrap> findByLimjang(Limjang limjang) {
    return scrapRepository.serachByLimjang(limjang);
  }
}
