package umc.th.juinjang.model.dto.checklist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.th.juinjang.model.entity.enums.ChecklistQuestionCategory;
import umc.th.juinjang.model.entity.enums.ChecklistQuestionType;

public class ChecklistAnswerResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AnswerDto {
        private Long answerId;
        private Long questionId;
        private ChecklistQuestionCategory category;
        private Long limjangId;
        private String answer;
        private ChecklistQuestionType answerType;
    }
}
