package umc.th.juinjang.repository.limjang;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.Member;

@Repository
public interface LimjangRepository extends JpaRepository<Limjang, Long> {

  List<Limjang> findLimjangByMemberId(Member member);

  // 가장 최근에 update된 5개 순서대로
  List<Limjang> findTop5ByOrderByUpdatedAtDesc();
}
