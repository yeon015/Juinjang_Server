package umc.th.juinjang.service.LimjangService;

import java.util.List;
import umc.th.juinjang.model.dto.limjang.LimjangDetailResponseDTO;
import umc.th.juinjang.model.dto.limjang.LimjangMainViewListResponsetDTO;

import umc.th.juinjang.model.dto.limjang.LimjangTotalListResponseDTO;

public interface LimjangQueryService {
  LimjangTotalListResponseDTO.TotalListDto getLimjangTotalList();

  List<LimjangMainViewListResponsetDTO.ListDto> getLimjangMainList();

  LimjangTotalListResponseDTO.TotalListDto getLimjangSearchList(String keyword);

  LimjangDetailResponseDTO.DetailDto getLimjangDetail(Long limjangId);
}
