package umc.th.juinjang.repository.limjang;

import java.util.List;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.Member;

public interface LimjangQueryDslRepository {

  List<Limjang> searchLimjangs(Member member, String keyword);

  // List<Limjang> findTop5ByMemberIdOrderByUpdatedAtDesc(Member member);

  List<Limjang> findMainScreenContentsLimjang(Member member);

  Limjang findByIdWithLimjangPrice(long memberId, long limjangId);
}
