package umc.th.juinjang.repository.limjang;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.Member;

@Repository
public interface LimjangRepository extends JpaRepository<Limjang, Long> {

  List<Limjang> findLimjangByMemberId(Member member);

  // 가장 최근에 update된 5개 순서대로
  List<Limjang> findTop5ByMemberIdOrderByUpdatedAtDesc(Member member);

  @Query("SELECT l FROM Limjang l WHERE " +
      "l.memberId = :member AND " +
      "(REPLACE(LOWER(l.nickname), ' ', '') LIKE REPLACE(LOWER(CONCAT('%', :keyword, '%')), ' ', '') OR " +
      "REPLACE(LOWER(l.address), ' ', '') LIKE REPLACE(LOWER(CONCAT('%', :keyword, '%')), ' ', '') OR " +
      "REPLACE(LOWER(l.addressDetail), ' ', '') LIKE REPLACE(LOWER(CONCAT('%', :keyword, '%')), ' ', ''))")

  List<Limjang> searchLimjangs(@Param("member") Member member, @Param("keyword") String keyword);

  Optional<Limjang> findLimjangByLimjangIdAndMemberId(Long limjangId, Member member);

}


