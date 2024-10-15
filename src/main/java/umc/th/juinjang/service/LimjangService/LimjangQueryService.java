package umc.th.juinjang.service.LimjangService;

import umc.th.juinjang.model.dto.limjang.response.LimjangDetailGetResponse;

import umc.th.juinjang.model.dto.limjang.enums.LimjangSortOptions;
import umc.th.juinjang.model.dto.limjang.response.LimjangsGetByKeywordResponse;
import umc.th.juinjang.model.dto.limjang.response.LimjangsGetResponse;
import umc.th.juinjang.model.dto.limjang.response.LimjangsMainGetResponse;
import umc.th.juinjang.model.dto.limjang.response.LimjangsMainGetVersion2Response;
import umc.th.juinjang.model.entity.Member;

public interface LimjangQueryService {

  LimjangsGetResponse getLimjangTotalList(Member member, LimjangSortOptions sort);

  LimjangsMainGetResponse getLimjangsMain(Member member);

  LimjangsGetByKeywordResponse getLimjangSearchList(Member member, String keyword);

  LimjangDetailGetResponse getDetail(long id, Member member);

  LimjangsMainGetVersion2Response getLimjangsMainVersion2(Member member);
}
