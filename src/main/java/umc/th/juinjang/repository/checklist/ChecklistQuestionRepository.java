package umc.th.juinjang.repository.checklist;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.th.juinjang.model.entity.ChecklistQuestionShort;
import umc.th.juinjang.model.entity.enums.ChecklistQuestionCategory;
import umc.th.juinjang.model.entity.enums.LimjangPurpose;

import java.util.List;

public interface ChecklistQuestionRepository extends JpaRepository<ChecklistQuestionShort, Long> {
//    List<ChecklistQuestionShort> findChecklistQuestionsByPurpose(LimjangPurpose purpose);
//
//    List<ChecklistQuestionShort> findChecklistQuestionsByPurposeAndCategory(LimjangPurpose purpose, ChecklistQuestionCategory category);
}
