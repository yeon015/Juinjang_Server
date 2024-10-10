package umc.th.juinjang.repository.limjang;

import java.util.List;
import umc.th.juinjang.model.dto.limjang.enums.LimjangSortOptions;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.Member;

public interface LimjangQueryDslRepository {

  List<Limjang> searchLimjangsWhereDeletedIsFalse(Member member, String keyword);

  List<Limjang> findMainScreenContentsLimjang(Member member);

  List<Limjang> findAllByMemberAndDeletedIsFalseOrderByParam(Member member, LimjangSortOptions sort);
}
