package umc.th.juinjang.repository.limjang;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.Member;
import umc.th.juinjang.model.entity.Scrap;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
  Scrap findScrapByLimjangId(Limjang limjangId);
}