package umc.th.juinjang.service.LimjangService;

import java.util.List;
import umc.th.juinjang.model.dto.limjang.LimjangDeleteRequestDTO;
import umc.th.juinjang.model.dto.limjang.LimjangPostRequestDTO;
import umc.th.juinjang.model.dto.limjang.LimjangUpdateRequestDTO;
import umc.th.juinjang.model.dto.limjang.LimjangUpdateRequestDTO.UpdateDto;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.Member;

public interface LimjangCommandService {

  Limjang postLimjang(LimjangPostRequestDTO.PostDto request, Member member);

  void deleteLimjangs(LimjangDeleteRequestDTO.DeleteDto deleteIds);

  void updateLimjang(long memberId, long limjangId, LimjangUpdateRequestDTO.UpdateDto requestUpdateInfo);
}
