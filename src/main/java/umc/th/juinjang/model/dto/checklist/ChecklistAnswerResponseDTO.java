package umc.th.juinjang.model.dto.checklist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ChecklistAnswerResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AnswerDto {
        private Long answerId;
        private Long questionId;
        private Long limjangId;
        private String answer;
    }
}
