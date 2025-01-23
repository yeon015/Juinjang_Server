package umc.th.juinjang.service.image;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.th.juinjang.apiPayload.code.status.ErrorStatus;
import umc.th.juinjang.apiPayload.exception.handler.LimjangHandler;
import umc.th.juinjang.converter.image.ImageListConverter;
import umc.th.juinjang.model.dto.image.ImageListResponseDTO;
import umc.th.juinjang.model.entity.Image;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.repository.image.ImageRepository;
import umc.th.juinjang.repository.limjang.LimjangRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageQueryServiceImpl implements ImageQueryService {

  private final ImageRepository imageRepository;
  private final LimjangRepository limjangRepository;

  @Override
  @Transactional(readOnly = true)
  public ImageListResponseDTO.ImagesListDTO getImageList(Long limjangId) {
    Limjang findLimjang = limjangRepository.findById(limjangId)
        .orElseThrow(() -> new LimjangHandler(ErrorStatus.LIMJANG_NOTFOUND_ERROR));

    List<Image> imageList = imageRepository.findImagesByLimjangId(findLimjang);

    return ImageListConverter.toImageListDto(imageList);

  }
}
