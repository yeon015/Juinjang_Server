package umc.th.juinjang.model.dto.checklist;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChecklistAnswerAndReportResponseDTO {
    private List<ChecklistAnswerResponseDTO.AnswerDto> answerDtoList;
    private ReportResponseDTO reportDto;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReportResponseDTO {
        private Long reportId;
        private String indoorKeyWord;
        private String publicSpaceKeyWord;
        private String locationConditionsWord;
        private Float indoorRate;
        private Float publicSpaceRate;
        private Float locationConditionsRate;
        private Float totalRate;
    }
}
