package umc.th.juinjang.service.LimjangService;

import java.util.List;
import umc.th.juinjang.model.dto.limjang.LimjangMainViewListResponsetDTO;
import umc.th.juinjang.model.dto.limjang.LimjangMainViewListResponsetDTO.ListDto;

import umc.th.juinjang.model.dto.limjang.LimjangTotalListRequestDTO;
import umc.th.juinjang.model.dto.limjang.LimjangTotalListResponseDTO;
import umc.th.juinjang.model.entity.Limjang;

public interface LimjangQueryService {
  LimjangTotalListResponseDTO.TotalListDto getLimjangTotalList();

  List<LimjangMainViewListResponsetDTO.ListDto> getLimjangMainList();

  LimjangTotalListResponseDTO.TotalListDto getLimjangSearchList(String keyword);

}
