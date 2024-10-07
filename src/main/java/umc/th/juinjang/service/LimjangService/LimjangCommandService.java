package umc.th.juinjang.service.LimjangService;

import umc.th.juinjang.model.dto.limjang.request.LimjangPatchRequest;
import umc.th.juinjang.model.dto.limjang.request.LimjangPostRequest;
import umc.th.juinjang.model.dto.limjang.request.LimjangUpdateRequestDTO;
import umc.th.juinjang.model.dto.limjang.request.LimjangsDeleteRequest;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.Member;

public interface LimjangCommandService {

  Limjang postLimjang(LimjangPostRequest request, Member member);

  void deleteLimjangs(LimjangsDeleteRequest deleteIds, Member member);

  void updateLimjang(Member member, long limjangId, LimjangPatchRequest request);
}
