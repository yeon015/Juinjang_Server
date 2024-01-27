package umc.th.juinjang.converter.image;

import java.util.List;
import java.util.Optional;
import umc.th.juinjang.model.dto.image.ImageListResponseDTO;
import umc.th.juinjang.model.dto.image.ImageUploadResponseDTO;
import umc.th.juinjang.model.entity.Image;

public class ImageListConverter {

  public static ImageListResponseDTO.ImagesListDTO toImageListDto(List<Image> images){
    List<ImageListResponseDTO.ImageDTO> imageDtoList =
        images.stream().map(ImageListConverter::toImageDto).toList();

    return ImageListResponseDTO.ImagesListDTO
        .builder()
        .images(imageDtoList)
        .build();
  }

  public static ImageListResponseDTO.ImageDTO toImageDto(Image image){
      return ImageListResponseDTO.ImageDTO.builder()
          .imageId(image.getImageId())
          .imageUrl(image.getImageUrl())
          .build();
  }
}
