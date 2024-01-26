package umc.th.juinjang.repository.record;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.th.juinjang.model.entity.Record;

public interface RecordRepository extends JpaRepository<Record, Long> {
}
