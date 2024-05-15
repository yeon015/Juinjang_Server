package umc.th.juinjang.repository.limjang;

import java.util.List;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.Member;

public interface LimjangQueryDslRepository {

  List<Limjang> searchLimjangs(Member member, String keyword)

}
