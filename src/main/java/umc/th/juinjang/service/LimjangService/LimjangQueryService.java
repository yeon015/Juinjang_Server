package umc.th.juinjang.service.LimjangService;

import java.util.List;
import umc.th.juinjang.model.dto.limjang.response.LimjangDetailResponseDTO;
import umc.th.juinjang.model.dto.limjang.response.LimjangMainViewListResponsetDTO;

import umc.th.juinjang.model.dto.limjang.response.LimjangTotalListResponseDTO;
import umc.th.juinjang.model.dto.limjang.enums.LimjangSortOptions;
import umc.th.juinjang.model.dto.limjang.response.LimjangsGetResponse;
import umc.th.juinjang.model.entity.Member;

public interface LimjangQueryService {

  LimjangsGetResponse getLimjangTotalList(Member member, LimjangSortOptions sort);

  List<LimjangMainViewListResponsetDTO.ListDto> getLimjangMainList(Member member);

  LimjangTotalListResponseDTO.TotalListDto getLimjangSearchList(Member member, String keyword);

  LimjangDetailResponseDTO.DetailDto getLimjangDetail(Long limjangId);
}
