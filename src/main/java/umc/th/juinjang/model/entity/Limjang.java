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
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;
import umc.th.juinjang.model.entity.common.BaseEntity;
import umc.th.juinjang.model.entity.enums.LimjangPropertyType;
import umc.th.juinjang.model.entity.enums.LimjangPriceType;
import umc.th.juinjang.model.entity.enums.LimjangPurpose;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
//@Where(clause = "deleted = false")
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
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "price_id", referencedColumnName = "price_id")
  private LimjangPrice limjangPrice;

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

  private String addressDetail;

  // 집 별명
  @Column(nullable = false)
  private String nickname;

  @Column(columnDefinition = "text")
  private String memo;

  // 양방향 매핑
  @OneToMany(mappedBy = "limjangId", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ChecklistAnswer> answerList = new ArrayList<>();

  @OneToOne(mappedBy = "limjangId", cascade = CascadeType.ALL, orphanRemoval = true)
  private Report report;

  @OneToMany(mappedBy = "limjangId", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Record> recordList = new ArrayList<>();

  @OneToMany(mappedBy = "limjangId", cascade = CascadeType.ALL, orphanRemoval = true)
  @BatchSize(size = 100)
  private List<Image> imageList = new ArrayList<>();

  @Column(name = "record_count")
  @ColumnDefault("0") //default 0
  private int recordCount;

  @Column(nullable = false, name = "deleted")
  private boolean deleted = Boolean.FALSE;

  public void saveMemberAndPrice(Member member, LimjangPrice limjangPrice){
    this.limjangPrice = limjangPrice;
    this.memberId = member;
  }

  public void updateLimjang(String address, String addressDetail, String nickname, LimjangPriceType priceType){
    this.address = address;
    this.addressDetail = addressDetail;
    this.nickname = nickname;
    this.priceType = priceType;
  }

  public void updateMemo(String memo){
    this.memo = memo;
  }
  public void saveImages(Image image){
    this.imageList.add(image);
  }

  public String getDefaultImage() {
    return this.imageList.isEmpty() ? null :this.imageList.get(0).getImageUrl();
  }


}