package umc.th.juinjang.service.LimjangService;

import umc.th.juinjang.model.dto.limjang.response.LimjangDetailResponseDTO;

import umc.th.juinjang.model.dto.limjang.response.LimjangTotalListResponseDTO;
import umc.th.juinjang.model.dto.limjang.enums.LimjangSortOptions;
import umc.th.juinjang.model.dto.limjang.response.LimjangsGetResponse;
import umc.th.juinjang.model.dto.limjang.response.LimjangsMainGetResponse;
import umc.th.juinjang.model.entity.Member;

public interface LimjangQueryService {

  LimjangsGetResponse getLimjangTotalList(Member member, LimjangSortOptions sort);

  LimjangsMainGetResponse getLimjangsMain(Member member);

  LimjangTotalListResponseDTO.TotalListDto getLimjangSearchList(Member member, String keyword);

  LimjangDetailResponseDTO.DetailDto getLimjangDetail(Long limjangId);
}
