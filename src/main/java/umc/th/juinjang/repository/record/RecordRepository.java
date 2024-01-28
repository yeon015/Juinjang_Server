package umc.th.juinjang.repository.record;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.Record;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    public List<Record> findAllByLimjangIdOrderByRecordIdDesc(Limjang limjang);

    public List<Record> findTop3ByLimjangIdOrderByRecordIdDesc(Limjang limjang);


    @Modifying
    @Transactional
    @Query("UPDATE Record r SET r.recordScript = :content WHERE r.recordId = :recordId")
    void updateRecordContent(@Param("recordId") Long recordId,@Param("content") String content);

    @Modifying
    @Transactional
    @Query("UPDATE Record r SET r.recordName = :recordName WHERE r.recordId = :recordId")
    void updateRecordTitle(@Param("recordId") Long recordId,@Param("recordName") String recordName);


}
