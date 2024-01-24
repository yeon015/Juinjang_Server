package umc.th.juinjang.repository.checklist;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.th.juinjang.model.entity.ChecklistAnswer;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.Report;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findByLimjangId(Limjang limjang);
}
