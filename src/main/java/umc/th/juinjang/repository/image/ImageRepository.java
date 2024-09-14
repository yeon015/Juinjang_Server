package umc.th.juinjang.repository.image;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import umc.th.juinjang.model.entity.Image;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.Record;

public interface ImageRepository extends JpaRepository<Image, Long> {

  List<Image> findImagesByLimjangId(Limjang limjang);

    void deleteAllByLimjangId(Limjang limjang);
}
