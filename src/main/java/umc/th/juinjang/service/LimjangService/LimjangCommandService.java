package umc.th.juinjang.service.LimjangService;

import java.util.List;
import umc.th.juinjang.model.dto.limjang.LimjangDeleteRequestDTO;
import umc.th.juinjang.model.dto.limjang.LimjangPostRequestDTO;
import umc.th.juinjang.model.entity.Limjang;

public interface LimjangCommandService {

  Limjang postLimjang(LimjangPostRequestDTO.PostDto request);

  void deleteLimjangs(List<Long> ids
      );
}
