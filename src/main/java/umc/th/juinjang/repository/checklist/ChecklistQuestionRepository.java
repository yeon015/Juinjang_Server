package umc.th.juinjang.repository.checklist;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.th.juinjang.model.entity.ChecklistQuestion;
import umc.th.juinjang.model.entity.enums.ChecklistQuestionCategory;
import umc.th.juinjang.model.entity.enums.ChecklistQuestionVersion;
import umc.th.juinjang.model.entity.enums.LimjangPurpose;

import java.util.List;

public interface ChecklistQuestionRepository extends JpaRepository<ChecklistQuestion, Long> {
    List<ChecklistQuestion> findChecklistQuestionsByPurpose(LimjangPurpose purpose);

    List<ChecklistQuestion> findChecklistQuestionsByPurposeAndCategory(LimjangPurpose purpose, ChecklistQuestionCategory category);
}
