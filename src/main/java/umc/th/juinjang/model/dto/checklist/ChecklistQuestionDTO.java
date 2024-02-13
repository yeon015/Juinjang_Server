package umc.th.juinjang.model.dto.checklist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


public class ChecklistQuestionDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionListDto {
        private Integer category;
        private List<QuestionDto> questionDtos;
    }

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
        private List<OptionDto> options;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OptionDto {
        private Integer indexNum;
        private Long questionId;
        private String optionValue;
    }
}
