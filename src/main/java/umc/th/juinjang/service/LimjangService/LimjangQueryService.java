package umc.th.juinjang.service.LimjangService;

import umc.th.juinjang.model.dto.limjang.LimjangPostRequestDTO;
import umc.th.juinjang.model.dto.limjang.LimjangPostResponseDTO;
import umc.th.juinjang.model.dto.limjang.LimjangTotalListResponseDTO;
import umc.th.juinjang.model.entity.Limjang;

public interface LimjangQueryService {
  LimjangTotalListResponseDTO.TotalListDto getLimjangTotalList();

}
