package umc.th.juinjang.repository.checklist;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.th.juinjang.model.entity.ChecklistAnswer;
import umc.th.juinjang.model.entity.Limjang;

import java.util.List;

public interface ChecklistAnswerRepository extends JpaRepository<ChecklistAnswer, Long> {
    List<ChecklistAnswer> findChecklistAnswerByLimjangId(Limjang limjang);
    void deleteAllByLimjangId(Limjang limjang);
}
