package umc.th.juinjang.repository.limjang;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import umc.th.juinjang.config.TestConfig;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.Member;
import umc.th.juinjang.model.entity.enums.LimjangPriceType;
import umc.th.juinjang.model.entity.enums.LimjangPropertyType;
import umc.th.juinjang.model.entity.enums.LimjangPurpose;
import umc.th.juinjang.model.entity.enums.MemberProvider;

@DataJpaTest
@Import(TestConfig.class)
public class LimjangQuerydslTest {

  @Autowired
  private LimjangRepository limjangRepository;

  @Autowired
  private MemberRepository memberRepository;

  @Test
  @DisplayName("키워드를 전달하면 멤버가 소유한 게시글 중 닉네임, 주소, 상세주소 컬럼중 하나라도 키워드를 포함하는 게시글을 조회할 수 있다.")
  void searchLimjangsByKeyword() {

    // given
    Member member = createMember(1L);
    memberRepository.save(member);
    Limjang limjang1 = createLimjang(member, 1L, "경기도 구리시 인창동", "삼성아파트", "우리 집");
    Limjang limjang2 = createLimjang(member, 2L, "경기도 구리시", "인창", "우리 집");
    Limjang limjang3 = createLimjang(member, 3L, "경기도 구리시", "어쩌구", "인창");
    Limjang limjang4 = createLimjang(member, 4L, "어쩌구", "어쩌구", "어쩌구");
    limjangRepository.saveAll(List.of(limjang1, limjang2, limjang3, limjang4));

    // when
    String keyword = "인창";
    List<Limjang> findLimjangs = limjangRepository.searchLimjangs(member, keyword);

    // then
    assertThat(findLimjangs)
        .extracting(Limjang::getLimjangId)
        .containsExactlyInAnyOrder(1L, 2L, 3L);
  }


  private Member createMember(Long id) {
    return Member.builder()
        .memberId(id)
        .email("seojin5565@gmail.com")
        .refreshToken("abcd")
        .refreshTokenExpiresAt(LocalDateTime.now())
        .provider(MemberProvider.KAKAO)
        .build();
  }

  private Limjang createLimjang(Member member, Long id, String address, String addressDetail, String nickname) {
    return Limjang.builder()
        .memberId(member)
        .limjangId(id)
        .purpose(LimjangPurpose.find(0))
        .propertyType(LimjangPropertyType.find(0))
        .priceType(LimjangPriceType.find(3))
        .address(address)
        .addressDetail(addressDetail)
        .nickname(nickname)
        .build();
  }
}
