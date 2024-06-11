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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import umc.th.juinjang.model.dto.limjang.LimjangUpdateRequestDTO;
import umc.th.juinjang.model.entity.common.BaseEntity;
import umc.th.juinjang.model.entity.enums.LimjangPropertyType;
import umc.th.juinjang.model.entity.enums.LimjangPriceType;
import umc.th.juinjang.model.entity.enums.LimjangPurpose;
import umc.th.juinjang.utils.LimjangUtil;
import umc.th.juinjang.validation.annotation.VaildPriceListSize;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Where(clause = "deleted = false")
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
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "price_id", referencedColumnName = "price_id")
  private LimjangPrice priceId;

  // 거래 목적
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private LimjangPurpose purpose;

  // 매물 유형
  @Enumerated(EnumType.STRING)
  private LimjangPropertyType propertyType;

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

  @OneToOne(mappedBy = "limjangId", cascade = CascadeType.ALL)
  private Report report;

  @OneToMany(mappedBy = "limjangId", cascade = CascadeType.ALL)
  private List<Record> recordList = new ArrayList<>();

  @OneToMany(mappedBy = "limjangId", cascade = CascadeType.ALL)
  private List<Image> imageList = new ArrayList<>();

  @OneToOne(mappedBy = "limjangId", cascade = CascadeType.ALL)
  private Scrap scrap;

  @Column(name = "record_count")
  @ColumnDefault("0") //default 0
  private int recordCount;

  @Column(nullable = false, name = "deleted")
  private boolean deleted = Boolean.FALSE;

  public void postLimjang(Member member, LimjangPrice limjangPrice){
    this.priceId = limjangPrice;
    this.memberId = member;
  }

  public void addScrap(Scrap scrap) {
    this.scrap = scrap;
    scrap.saveLimjang(this);
  }

  public void removeScrap(){
    this.scrap = null;
  }

  public void updateLimjang(Limjang newLimjang){
      this.address = newLimjang.getAddress();
      this.addressDetail = newLimjang.getAddressDetail();
      this.nickname = newLimjang.getNickname();
      this.priceType = newLimjang.getPriceType();
  }

  public void updateMemo(String memo){
    this.memo = memo;
  }
  public void saveImages(Image image){
    this.imageList.add(image);
  }

  public void saveReport(Report report){
    this.report = report;
  }

}
