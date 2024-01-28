package umc.th.juinjang.model.dto.record;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;


public class RecordResponseDTO {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RecordDto{
        private String recordName;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String recordScript;
        private Long recordTime;
        private String recordUrl;
        private Long recordId;
        private Long limjangId;
    }
}
