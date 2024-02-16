package umc.th.juinjang.converter.checklist;


import umc.th.juinjang.model.dto.checklist.ChecklistQuestionDTO;
import umc.th.juinjang.model.entity.ChecklistAnswerOption;
import umc.th.juinjang.model.entity.ChecklistQuestion;


import java.util.List;
import java.util.stream.Collectors;

public class ChecklistQuestionConverter {
    public static List<ChecklistQuestionDTO.QuestionDto> toChecklistQuestionListDTO(List<ChecklistQuestion> checklistQuestions) {
        return checklistQuestions.stream()
                .map(ChecklistQuestionConverter::questionDto).collect(Collectors.toList());
    }
    public static List<ChecklistQuestionDTO.OptionDto> toChecklistAnswerOptionDTO(List<ChecklistAnswerOption> checklistAnswerOptions) {
        return checklistAnswerOptions.stream()
                .map(ChecklistQuestionConverter::optionDto).collect(Collectors.toList());
    }

    public static ChecklistQuestionDTO.OptionDto optionDto(ChecklistAnswerOption checklistAnswerOption) {
        return ChecklistQuestionDTO.OptionDto.builder()
                .indexNum(checklistAnswerOption.getIndexNum())
                .questionId(checklistAnswerOption.getQuestionId().getQuestionId())
                .optionValue(checklistAnswerOption.getOptionValue())
                .build();
    }

    public static ChecklistQuestionDTO.QuestionDto questionDto(ChecklistQuestion checklistQuestion) {
        return ChecklistQuestionDTO.QuestionDto.builder()
                .questionId(checklistQuestion.getQuestionId())
                .category(checklistQuestion.getCategory().getValue())
                .subCategory(checklistQuestion.getSubCategory())
                .question(checklistQuestion.getQuestion())
                .version(checklistQuestion.getPurpose().getValue())
                .answerType(checklistQuestion.getAnswerType().getValue())
                .options(ChecklistQuestionConverter.toChecklistAnswerOptionDTO(checklistQuestion.getAnswerOptions()))
                .build();
    }

}
