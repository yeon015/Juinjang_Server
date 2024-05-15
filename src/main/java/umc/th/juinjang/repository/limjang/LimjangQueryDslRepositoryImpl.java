package umc.th.juinjang.repository.limjang;

import static umc.th.juinjang.model.entity.QLimjang.*;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.Member;

public class LimjangQueryDslRepositoryImpl implements LimjangQueryDslRepository {
  private final JPAQueryFactory queryFactory;

  public LimjangQueryDslRepositoryImpl(EntityManager em) {
    this.queryFactory = new JPAQueryFactory(em);
  }

  @Override
  public List<Limjang> searchLimjangs(Member member, String keyword) {
    String pKeyword = "%" + keyword.toLowerCase().replaceAll(" ", "") + "%";

    return queryFactory
        .selectFrom(limjang)
        .where(limjang.memberId.eq(member),
            keywordOf(
                removeBlank(limjang.nickname).like(pKeyword),
                removeBlank(limjang.address).like(pKeyword),
                removeBlank(limjang.addressDetail).like(pKeyword)
            ))
        .fetch();
  }

  private BooleanExpression keywordOf(BooleanExpression... conditions) {
    BooleanExpression result = null;
    for (BooleanExpression condition : conditions) {
      result = result == null ? condition : result.or(condition);
    }
    return result;
  }

  private StringExpression removeBlank(StringExpression origin) {
      return Expressions.stringTemplate("function('replace', function('lower', {0}), ' ', '')", origin);
  }
}
