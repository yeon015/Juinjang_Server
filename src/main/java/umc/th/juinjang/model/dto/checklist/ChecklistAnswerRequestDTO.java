package umc.th.juinjang.model.dto.checklist;

import lombok.*;


public class ChecklistAnswerRequestDTO {

    @Builder
    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AnswerDto {
        private Long questionId;
        private String answer;
    }
}
