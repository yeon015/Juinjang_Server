package umc.th.juinjang.model.dto.record;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;


public class RecordRequestDTO {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder

    public static class RecordDto {
        private Long limjangId;
        private Long recordTime;
        private String recordName;
        private String recordScript;
    }
}
