package umc.th.juinjang.repository.limjang;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.LimjangPrice;
import umc.th.juinjang.model.entity.Member;

public interface LimjangRepository extends JpaRepository<Limjang, Long> {

  List<Limjang> findLimjangByMemberId(Member member);
}
