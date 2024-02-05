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
        private String recordScript;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RecordTitleDto{
        private String recordName;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RecordContentDto{
        private String recordScript;
    }
}
