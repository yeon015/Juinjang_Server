package umc.th.juinjang.repository.checklist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import umc.th.juinjang.model.entity.ChecklistAnswer;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.Report;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findByLimjangId(Limjang limjang);

    void deleteAllByLimjangId(Limjang limjang);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM report r WHERE r.limjang_id = :limjangId", nativeQuery = true)
    void deleteByLimjangId(@Param("limjangId") Long limjangId);
}
