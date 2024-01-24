package umc.th.juinjang.model.dto.checklist;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChecklistAnswerAndReportResponseDTO {
    private List<ChecklistAnswerResponseDTO.AnswerDto> answerDtoList;
    private ReportResponseDTO.ReportDTO reportDto;

}
