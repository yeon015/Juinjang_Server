package umc.th.juinjang.service.LimjangService;

import umc.th.juinjang.model.dto.limjang.request.LimjangDeleteRequestDTO;
import umc.th.juinjang.model.dto.limjang.request.LimjangPostRequest;
import umc.th.juinjang.model.dto.limjang.request.LimjangUpdateRequestDTO;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.Member;

public interface LimjangCommandService {

  Limjang postLimjang(LimjangPostRequest request, Member member);

  void deleteLimjangs(LimjangDeleteRequestDTO.DeleteDto deleteIds);

  void updateLimjang(long memberId, long limjangId, LimjangUpdateRequestDTO.UpdateDto requestUpdateInfo);
}
