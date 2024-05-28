package umc.th.juinjang.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.th.juinjang.model.entity.common.BaseEntity;
import umc.th.juinjang.model.entity.enums.ChecklistQuestionCategory;
import umc.th.juinjang.model.entity.enums.ChecklistQuestionType;
import umc.th.juinjang.model.entity.enums.LimjangPurpose;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChecklistQuestionShort extends BaseEntity {

  @Id
  @Column(name="question_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long questionId;

  @Column(nullable = false)
  @Enumerated(EnumType.ORDINAL)
  private ChecklistQuestionCategory category;

  @Column(nullable = false)
  @Enumerated(EnumType.ORDINAL)
  private ChecklistQuestionType answerType;

  @OneToMany(mappedBy = "questionId", cascade = CascadeType.ALL)
  private List<ChecklistAnswer> answerList = new ArrayList<>();



}
