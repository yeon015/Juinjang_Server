package umc.th.juinjang.model.dto.checklist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class ChecklistQuestionDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionDto {
        private Long questionId;
        private Integer category;
        private String subCategory;
        private String question;
        private Integer version;
        private Integer answerType;
    }
}
