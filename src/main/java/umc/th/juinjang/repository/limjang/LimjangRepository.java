package umc.th.juinjang.repository.limjang;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.Member;

@Repository
public interface LimjangRepository extends JpaRepository<Limjang, Long>, LimjangQueryDslRepository {

  List<Limjang> findLimjangByMemberId(Member member);

  @Modifying
  @Query("UPDATE Limjang l SET l.deleted = true, l.updatedAt = CURRENT_TIMESTAMP WHERE l.limjangId = :limjangId")
  void softDeleteById(@Param("limjangId") Long limjangId);

  @Modifying
  @Query(value ="DELETE FROM limjang l WHERE l.deleted = true AND l.updated_at < :dateTime", nativeQuery = true)
  void hardDelete(@Param("dateTime") LocalDateTime dateTime);

  // 가장 최근에 update된 5개 순서대로
  List<Limjang> findTop5ByMemberIdOrderByUpdatedAtDesc(Member member);

  Optional<Limjang> findLimjangByLimjangIdAndMemberId(Long limjangId, Member member);

  @Modifying
  @Transactional
  @Query("UPDATE Limjang l SET l.recordCount = l.recordCount + 1 WHERE l.limjangId = :limjangId")
  void incrementRecordCount(@Param("limjangId") Long limjangId);

  @Modifying
  @Transactional
  @Query("UPDATE Limjang l SET l.memo = :memo WHERE l.limjangId = :limjangId")
  void updateMemo(@Param("limjangId") Long limjangId, @Param("memo") String memo);


  void deleteByMemberId(Long memberId);
}


