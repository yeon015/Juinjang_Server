package umc.th.juinjang.repository.record;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.th.juinjang.model.entity.Record;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
}
