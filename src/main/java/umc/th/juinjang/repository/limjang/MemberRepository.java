package umc.th.juinjang.repository.limjang;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    Optional<Member> findByRefreshToken(String refreshToken);

    Optional<Member> findByKakaoTargetId(Long targetId);

    Member findByNickname(String nickname);

    Optional<Member> findByAppleSub(String sub);
}
