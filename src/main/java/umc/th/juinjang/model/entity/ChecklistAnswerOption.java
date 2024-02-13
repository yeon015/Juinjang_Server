package umc.th.juinjang.model.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.th.juinjang.model.entity.common.BaseEntity;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChecklistAnswerOption extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long optionId;

  private Integer indexNum;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "question_id")
  private ChecklistQuestion questionId;


  private String optionValue;

}
