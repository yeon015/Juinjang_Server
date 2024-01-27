package umc.th.juinjang.repository.image;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.th.juinjang.model.entity.Image;
import umc.th.juinjang.model.entity.Record;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
