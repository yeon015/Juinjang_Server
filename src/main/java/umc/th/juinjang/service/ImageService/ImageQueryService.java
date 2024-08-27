package umc.th.juinjang.service.ImageService;

import umc.th.juinjang.model.dto.image.ImageListResponseDTO;

public interface ImageQueryService {
  ImageListResponseDTO.ImagesListDTO getImageList(Long limjangId);
}
