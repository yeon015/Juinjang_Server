package umc.th.juinjang.model.dto.checklist;

import lombok.*;
import umc.th.juinjang.model.entity.ChecklistQuestion;
import umc.th.juinjang.model.entity.Limjang;


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
