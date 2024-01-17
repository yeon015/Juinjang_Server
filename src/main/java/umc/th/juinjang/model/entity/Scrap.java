package umc.th.juinjang.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
public class Scrap extends BaseEntity {

  @Id
  @Column(name="scrap_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long scrapId;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "limjang_id", referencedColumnName = "limjang_id")
  private Limjang limjangId;

}
