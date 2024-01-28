package umc.th.juinjang.service.ImageService;

import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import umc.th.juinjang.apiPayload.code.status.ErrorStatus;
import umc.th.juinjang.apiPayload.exception.handler.LimjangHandler;
import umc.th.juinjang.converter.image.ImageUploadConverter;
import umc.th.juinjang.model.entity.Image;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.repository.image.ImageRepository;
import umc.th.juinjang.repository.limjang.LimjangRepository;
import umc.th.juinjang.service.S3Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageCommandServiceImpl implements ImageCommandService {

  private final ImageRepository imageRepository;
  private final LimjangRepository limjangRepository;
  private final S3Service s3Service;

  @Override
  @Transactional
  public void uploadImages(Long limjangId, List<MultipartFile> images) {

    Limjang limjang = limjangRepository.findById(limjangId)
        .orElseThrow(()-> new LimjangHandler(ErrorStatus.LIMJANG_NOTFOUND_ERROR));

    images.forEach(it -> {
      try {
        if (!it.isEmpty()) {
          String storedFileName = s3Service.upload(it, "image");
          Image image = ImageUploadConverter.toImageDto(storedFileName, limjang);
          limjang.saveImages(image);
        }
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

  }

  @Override
  @Transactional
  public void deleteImages(List<Long> deleteIds) { //이미지 id로 삭제한다...!

      try {

        //s3에서 삭제
        List<Image> imageList = imageRepository.findAllById(deleteIds);

        imageList.forEach(image -> {
          s3Service.deleteFile(image.getImageUrl());
          imageRepository.deleteById(image.getImageId());
        });
      } catch (DataIntegrityViolationException e) {
        throw new LimjangHandler(ErrorStatus.IMAGE_DELETE_NOT_COMPLETE);
      } catch (EmptyResultDataAccessException e) {
        throw new LimjangHandler(ErrorStatus.IMAGE_DELETE_NOT_FOUND);
      }
  }
}
