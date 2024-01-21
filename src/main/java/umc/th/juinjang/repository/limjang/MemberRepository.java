package umc.th.juinjang.repository.limjang;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
