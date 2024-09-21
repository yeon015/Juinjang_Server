package umc.th.juinjang.repository.limjang;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.swing.text.html.Option;
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


  @Query(value = "SELECT * FROM limjang l WHERE l.member_id = :memberId", nativeQuery = true)
  List<Limjang> findLimjangByMemberIdIgnoreDeleted(@Param("memberId") Long memberId);

  Optional<Limjang> findByLimjangIdAndMemberIdAndDeletedIsFalse(Long id, Member member);

  List<Limjang> findAllByLimjangIdInAndMemberIdAndDeletedIsFalse(List<Long> id, Member member);

  @Modifying
  @Query("UPDATE Limjang l SET l.deleted = true WHERE l.limjangId in :ids")
  void softDeleteByIds(@Param("ids") List<Long> ids);

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

  @Transactional
  @Modifying
  @Query(value = "DELETE FROM limjang l WHERE l.member_id = :memberId", nativeQuery = true)
  void deleteAllByMemberId(@Param("memberId") Long memberId);

//  void deleteByMember(Member member);
}


