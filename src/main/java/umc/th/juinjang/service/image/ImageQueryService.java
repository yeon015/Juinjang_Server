package umc.th.juinjang.service.image;

import umc.th.juinjang.model.dto.image.ImageListResponseDTO;

public interface ImageQueryService {
  ImageListResponseDTO.ImagesListDTO getImageList(Long limjangId);
}
