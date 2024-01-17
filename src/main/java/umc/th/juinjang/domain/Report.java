package umc.th.juinjang.domain;

import com.fasterxml.jackson.databind.ser.Serializers.Base;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.util.Random;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.th.juinjang.domain.common.BaseEntity;

@Entity
@Getter
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
  private String indoorKeyWord;

  @Column(nullable = false)
  private String publicSpaceKeyWord;

  @Column(nullable = false)
  private String locationConditionsWord;

  @Column(nullable = false)
  private Float indoorRate;

  @Column(nullable = false)
  private Float publicSpaceRate;

  @Column(nullable = false)
  private Float locationConditionsRate;

  @Column(nullable = false)
  private Float totalRate;

  // ================ 이거 점검해주세요
  public String setRandomKeyword(Float rate){

    String[] oneToTwo = { "불안한" ,"불안정한" ,"불쾌한"};
    String[] twoToThree = {"평균적인", "보통의", "나쁘지 않은"};
    String[] threeToFour = {"좋은", "좋은 편인", "훌륭한", "쾌적한"};
    String[] fourToFive = {"최상의", "최고의", "상당히 좋은", "상당히 쾌적한"};

    Random random = new Random();

    if (rate >= 1 && rate < 2) {
      return oneToTwo[random.nextInt(oneToTwo.length)];
    } else if (rate >= 2 && rate < 3) {
      return twoToThree[random.nextInt(twoToThree.length)];
    } else if (rate >= 3 && rate < 4) {
      return threeToFour[random.nextInt(threeToFour.length)];
    } else if (rate >= 4 && rate <= 5) {
      return threeToFour[random.nextInt(threeToFour.length)];
    }

    return null;

  }

}
