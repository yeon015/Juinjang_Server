package umc.th.juinjang.repository.limjang;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.Member;
import umc.th.juinjang.model.entity.Scrap;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
  @Query("select s from Scrap s where s.limjangId = :limjang")
  Optional<Scrap> serachByLimjang(@Param("limjang")Limjang limjang);

  @Modifying
  @Query("delete from Scrap s where s.scrapId = :id")
  void deleteByScrapId(@Param("id")long id);

  @Transactional
  @Modifying
  @Query(value = "DELETE FROM scrap s WHERE s.limjang_id = :limjangId", nativeQuery = true)
  void deleteByLimjangId(@Param("limjangId") Long limjangId);

  List<Scrap> findAllByLimjangIdIn(List<Limjang> limjangs);
}