package umc.th.juinjang.service.ImageService;

import java.util.List;
import umc.th.juinjang.model.dto.image.ImageListResponseDTO;
import umc.th.juinjang.model.dto.limjang.LimjangDetailResponseDTO;
import umc.th.juinjang.model.dto.limjang.LimjangMainViewListResponsetDTO;
import umc.th.juinjang.model.dto.limjang.LimjangTotalListResponseDTO;

public interface ImageQueryService {
  ImageListResponseDTO.ImagesListDTO getImageList(Long limjangId);
}
