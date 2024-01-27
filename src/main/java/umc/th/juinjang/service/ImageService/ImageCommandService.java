package umc.th.juinjang.service.ImageService;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import umc.th.juinjang.model.dto.image.ImageUploadRequestDTO;
import umc.th.juinjang.model.dto.image.ImageUploadResponseDTO;
import umc.th.juinjang.model.dto.limjang.LimjangPostRequestDTO;
import umc.th.juinjang.model.dto.limjang.LimjangUpdateRequestDTO;
import umc.th.juinjang.model.entity.Limjang;

public interface ImageCommandService {
//  public void uploadImages(ImageUploadRequestDTO.ImageDto requestImages);
    void uploadImages(Long limjangId, List<MultipartFile> images);

}
