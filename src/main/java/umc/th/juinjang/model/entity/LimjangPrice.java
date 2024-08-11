package umc.th.juinjang.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.th.juinjang.model.entity.common.BaseEntity;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LimjangPrice extends BaseEntity {

  @Id
  @Column(name="price_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long priceId;

  private String marketPrice;

  private String sellingPrice;

  private String depositPrice;

  private String monthlyRent;

  private String pullRent;

  @OneToOne(mappedBy = "limjangPrice", cascade = CascadeType.ALL)
  private Limjang limjang;

  public void updateLimjangPriceList(LimjangPrice newLimjangPrice){
      this.marketPrice = newLimjangPrice.getMarketPrice();
      this.sellingPrice = newLimjangPrice.getSellingPrice();
      this.depositPrice = newLimjangPrice.getDepositPrice();
      this.monthlyRent = newLimjangPrice.getMonthlyRent();
      this.pullRent = newLimjangPrice.getPullRent();
  }
}
