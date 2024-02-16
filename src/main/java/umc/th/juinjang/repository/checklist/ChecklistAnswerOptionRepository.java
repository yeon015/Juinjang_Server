package umc.th.juinjang.repository.checklist;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.th.juinjang.model.entity.ChecklistAnswer;
import umc.th.juinjang.model.entity.ChecklistAnswerOption;
import umc.th.juinjang.model.entity.ChecklistQuestion;
import umc.th.juinjang.model.entity.Limjang;

import java.util.List;

public interface ChecklistAnswerOptionRepository extends JpaRepository<ChecklistAnswerOption, Long> {
    List<ChecklistAnswerOption> findAllByQuestionId(ChecklistQuestion question);

}
