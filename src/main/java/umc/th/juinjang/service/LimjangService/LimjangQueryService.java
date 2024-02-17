package umc.th.juinjang.service.LimjangService;

import java.util.List;
import umc.th.juinjang.model.dto.limjang.LimjangDetailResponseDTO;
import umc.th.juinjang.model.dto.limjang.LimjangMainViewListResponsetDTO;

import umc.th.juinjang.model.dto.limjang.LimjangTotalListResponseDTO;
import umc.th.juinjang.model.entity.Member;

public interface LimjangQueryService {

  LimjangTotalListResponseDTO.TotalListDto getLimjangTotalList(Member member, String sort);

  List<LimjangMainViewListResponsetDTO.ListDto> getLimjangMainList(Member member);

  LimjangTotalListResponseDTO.TotalListDto getLimjangSearchList(String keyword);

  LimjangDetailResponseDTO.DetailDto getLimjangDetail(Long limjangId);
}
