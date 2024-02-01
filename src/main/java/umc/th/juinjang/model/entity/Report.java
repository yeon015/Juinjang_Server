package umc.th.juinjang.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.util.Random;

import lombok.*;
import umc.th.juinjang.model.entity.common.BaseEntity;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Report extends BaseEntity {

  @Id
  @Column(name="report_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long reportId;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "limjang_id", referencedColumnName = "limjang_id")
  private Limjang limjangId;

  @Column(nullable = false)
  private String indoorKeyword;

  @Column(nullable = false)
  private String publicSpaceKeyword;

  @Column(nullable = false)
  private String locationConditionsKeyword;

  @Column(nullable = false)
  private Float indoorRate;

  @Column(nullable = false)
  private Float publicSpaceRate;

  @Column(nullable = false)
  private Float locationConditionsRate;

  @Column(nullable = false)
  private Float totalRate;

  public void setIndoorRateAndKeyword(Float categoryRate) {
    this.indoorRate = categoryRate;
    this.indoorKeyword = getRandomKeyword(categoryRate);
  }

  public void setPublicSpaceRateAndKeyword(Float categoryRate) {
    this.publicSpaceRate = categoryRate;
    this.publicSpaceKeyword = getRandomKeyword(categoryRate);
  }

  public void setLocationConditionsRateAndKeyword(Float categoryRate) {
    this.locationConditionsRate = categoryRate;
    this.locationConditionsKeyword = getRandomKeyword(categoryRate);
  }

  public String getRandomKeyword(Float rate){
    Random random = new Random();

    if (rate >= 1 && rate < 2) {
      String[] oneToTwo = { "불안한" ,"불안정한" ,"불쾌한"};
      return oneToTwo[random.nextInt(oneToTwo.length)];
    }

    if (rate >= 2 && rate < 3) {
      String[] twoToThree = {"평균적인", "보통의", "나쁘지 않은"};
      return twoToThree[random.nextInt(twoToThree.length)];
    }

    if (rate >= 3 && rate < 4) {
      String[] threeToFour = {"좋은", "좋은 편인", "훌륭한", "쾌적한"};
      return threeToFour[random.nextInt(threeToFour.length)];
    }

    if (rate >= 4 && rate <= 5) {
      String[] fourToFive = {"최상의", "최고의", "상당히 좋은", "상당히 쾌적한"};
      return fourToFive[random.nextInt(fourToFive.length)];
    }

    return "아직 미평가된";

  }

  // ================ 이거 점검해주세요


}
