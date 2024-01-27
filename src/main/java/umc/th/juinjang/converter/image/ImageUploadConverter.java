package umc.th.juinjang.converter.image;

import umc.th.juinjang.model.dto.image.ImageUploadRequestDTO;
import umc.th.juinjang.model.entity.Image;
import umc.th.juinjang.model.entity.Limjang;

public class ImageUploadConverter {

  public static Image toImageDto(String fileName, Limjang limjang){
    return Image.builder()
        .imageUrl(fileName)
        .limjangId(limjang)
        .build();
  }

}
