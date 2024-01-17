package umc.th.juinjang.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.th.juinjang.model.entity.common.BaseEntity;
import umc.th.juinjang.model.entity.enums.LimjangPropertyType;
import umc.th.juinjang.model.entity.enums.LimjangPriceType;
import umc.th.juinjang.model.entity.enums.LimjangPurpose;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Limjang extends BaseEntity {

  @Id
  @Column(name="limjang_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long limjangId;

  // 회원 ID
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member memberId;

  // 가격 ID
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "price_id", referencedColumnName = "price_id")
  private LimjangPrice priceId;

  // 거래 목적
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private LimjangPurpose purpose;

  // 매물 유형
  @Enumerated(EnumType.STRING)
  private LimjangPropertyType type;

  // 가격 유형
  @Enumerated(EnumType.STRING)
  private LimjangPriceType priceType;

  // 도로명 주소
  @Column(nullable = false)
  private String address;

  // 상세주소
  @Column(nullable = false)
  private String addressDetail;

  // 집 별명
  @Column(nullable = false)
  private String nickname;

  // ================= 이거 점검해주세요
  // 메모
  @Column(columnDefinition = "text")
  private String memo;

  // 양방향 매핑
  @OneToMany(mappedBy = "limjangId", cascade = CascadeType.ALL)
  private List<ChecklistAnswer> answerList = new ArrayList<>();

  @OneToOne(mappedBy = "limjangId")
  private Report report;

  @OneToMany(mappedBy = "limjangId", cascade = CascadeType.ALL)
  private List<Record> recordList = new ArrayList<>();

  @OneToMany(mappedBy = "limjangId", cascade = CascadeType.ALL)
  private List<Image> imageList = new ArrayList<>();

  @OneToOne(mappedBy = "limjangId")
  private Scrap scrap;

}
