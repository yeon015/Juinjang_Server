package umc.th.juinjang.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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

  @OneToOne(mappedBy = "priceId")
  private Limjang limjang;

}
