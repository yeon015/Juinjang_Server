package umc.th.juinjang.domain;

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
import umc.th.juinjang.domain.common.BaseEntity;
import umc.th.juinjang.domain.enums.ChecklistQuestionCategory;
import umc.th.juinjang.domain.enums.ChecklistQuestionVersion;

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

  private String question;

  @Column(nullable = false)
  private ChecklistQuestionVersion version;

  @Column(nullable = false)
  private ChecklistQuestionVersion answerType;

  @OneToMany(mappedBy = "questionId", cascade = CascadeType.ALL)
  private List<ChecklistAnswer> answerList = new ArrayList<>();

}
