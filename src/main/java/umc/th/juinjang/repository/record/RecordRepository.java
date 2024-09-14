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


    void deleteAllByLimjangId(Limjang limjang);
}
