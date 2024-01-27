package umc.th.juinjang.service.ImageService;

import static umc.th.juinjang.utils.LimjangUtil.determineLimjangPrice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import umc.th.juinjang.apiPayload.code.status.ErrorStatus;
import umc.th.juinjang.apiPayload.exception.handler.LimjangHandler;
import umc.th.juinjang.apiPayload.exception.handler.MemberHandler;
import umc.th.juinjang.converter.ImageUploadConverter;
import umc.th.juinjang.converter.limjang.LimjangPostConverter;
import umc.th.juinjang.converter.limjang.LimjangUpdateConverter;
import umc.th.juinjang.model.dto.image.ImageUploadRequestDTO;
import umc.th.juinjang.model.dto.image.ImageUploadRequestDTO.ImageDto;
import umc.th.juinjang.model.dto.image.ImageUploadResponseDTO;
import umc.th.juinjang.model.dto.image.ImageUploadResponseDTO.ImagesDTO;
import umc.th.juinjang.model.dto.limjang.LimjangPostRequestDTO.PostDto;
import umc.th.juinjang.model.dto.limjang.LimjangUpdateRequestDTO;
import umc.th.juinjang.model.entity.Image;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.LimjangPrice;
import umc.th.juinjang.model.entity.Member;
import umc.th.juinjang.repository.image.ImageRepository;
import umc.th.juinjang.repository.limjang.LimjangPriceRepository;
import umc.th.juinjang.repository.limjang.LimjangRepository;
import umc.th.juinjang.repository.limjang.MemberRepository;
import umc.th.juinjang.service.S3Uploader;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageCommandServiceImpl implements ImageCommandService {

  private final ImageRepository imageRepository;
  private final LimjangRepository limjangRepository;
  private final S3Uploader s3Uploader;

  @Override
  @Transactional
  public void uploadImages(Long limjangId, List<MultipartFile> images) {

    Limjang limjang = limjangRepository.findById(limjangId)
        .orElseThrow(()-> new LimjangHandler(ErrorStatus.LIMJANG_NOTFOUND_ERROR));

    images.forEach(it -> {
      try {
        if (!it.isEmpty()) {
          String storedFileName = s3Uploader.upload(it, "image");
          Image image = ImageUploadConverter.toImageDto(storedFileName, limjang);
          limjang.saveImages(image);
        }
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

  }
}
