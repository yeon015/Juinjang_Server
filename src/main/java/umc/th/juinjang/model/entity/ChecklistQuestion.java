package umc.th.juinjang.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
import umc.th.juinjang.model.entity.enums.ChecklistQuestionVersion;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChecklistQuestion extends BaseEntity {

  @Id
  @Column(name="question_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long questionId;

  @Column(nullable = false)
  private ChecklistQuestionCategory category;

  @Column(nullable = false)
  private String subCategory;

  private String question;

  @Column(nullable = false)
  private ChecklistQuestionVersion version;

  @Column(nullable = false)
  private ChecklistQuestionType answerType;

  @OneToMany(mappedBy = "questionId", cascade = CascadeType.ALL)
  private List<ChecklistAnswer> answerList = new ArrayList<>();

}
