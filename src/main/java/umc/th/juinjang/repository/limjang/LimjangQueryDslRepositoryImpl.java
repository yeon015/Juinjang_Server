package umc.th.juinjang.repository.limjang;

import static umc.th.juinjang.model.entity.QImage.image;
import static umc.th.juinjang.model.entity.QLimjang.limjang;
import static umc.th.juinjang.model.entity.QLimjangPrice.limjangPrice;
import static umc.th.juinjang.model.entity.QReport.report;
import static umc.th.juinjang.model.entity.QScrap.scrap;
import static com.querydsl.core.group.GroupBy.list;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.Member;

public class LimjangQueryDslRepositoryImpl implements LimjangQueryDslRepository {
  private final JPAQueryFactory queryFactory;

  public LimjangQueryDslRepositoryImpl(EntityManager em) {
    this.queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, em);
  }

  @Override
  public List<Limjang> searchLimjangs(Member member, String keyword) {
    String rKeyword = keyword.replaceAll(" ", "");

    return queryFactory
        .selectFrom(limjang)
        .leftJoin(limjang.report, report).fetchJoin()
        .leftJoin(limjang.scrap, scrap).fetchJoin()
        .leftJoin(limjang.limjangPrice, limjangPrice).fetchJoin()
        .leftJoin(limjang.imageList, image).fetchJoin()
        .where(limjang.memberId.eq(member),
            keywordOf(
                removeBlank(limjang.nickname).containsIgnoreCase(rKeyword),
                removeBlank(limjang.address).containsIgnoreCase(rKeyword),
                removeBlank(limjang.addressDetail).containsIgnoreCase(rKeyword)
            ))
        .fetch();
  }

  @Override
  public Limjang findByIdWithLimjangPrice(long memberId, long limjangId) {
    return queryFactory
        .selectFrom(limjang)
        .join(limjang.limjangPrice, limjangPrice).fetchJoin()
        .leftJoin(limjang.report, report).fetchJoin()
        .leftJoin(limjang.scrap, scrap).fetchJoin()
        .where(limjang.memberId.memberId.eq(memberId))
        .where(limjang.limjangId.eq(limjangId))
        .fetchOne();
  }

  private BooleanExpression keywordOf(BooleanExpression... conditions) {
    BooleanExpression result = null;
    for (BooleanExpression condition : conditions) {
      result = result == null ? condition : result.or(condition);
    }
    return result;
  }

  private StringExpression removeBlank(StringExpression origin) {
    return Expressions.stringTemplate("function('replace', {0}, ' ', '')", origin);
  }

  @Override
  public List<Limjang> findMainScreenContentsLimjang(Member member) {

    return queryFactory
        .selectFrom(limjang)
        .leftJoin(limjang.report, report).fetchJoin()
        .leftJoin(limjang.scrap, scrap).fetchJoin()
        .leftJoin(limjang.limjangPrice, limjangPrice).fetchJoin()
        .leftJoin(limjang.imageList, image).fetchJoin()
        .where(limjang.memberId.eq(member))
        .orderBy(limjang.updatedAt.desc())
        .limit(5)
        .fetch();
  }

  @Override
  public List<Limjang> findAllLimjangs(Member member) {
    return queryFactory
        .selectFrom(limjang)
        .leftJoin(limjang.report, report).fetchJoin()
        .leftJoin(limjang.scrap, scrap).fetchJoin()
        .leftJoin(limjang.limjangPrice, limjangPrice).fetchJoin()
        .leftJoin(limjang.imageList, image).fetchJoin()
        .where(limjang.memberId.eq(member))
        .fetch();
  }
}
