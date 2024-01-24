package umc.th.juinjang.converter.checklist;

import umc.th.juinjang.model.dto.checklist.ChecklistAnswerAndReportResponseDTO;
import umc.th.juinjang.model.dto.checklist.ChecklistAnswerResponseDTO;
import umc.th.juinjang.model.entity.ChecklistAnswer;
import umc.th.juinjang.model.entity.Report;

import java.util.List;
import java.util.stream.Collectors;

public class ChecklistAnswerAndReportConverter {

    public static ChecklistAnswerAndReportResponseDTO toDto(List<ChecklistAnswer> answerList, Report report) {
        List<ChecklistAnswerResponseDTO.AnswerDto> answerDtoList = answerList.stream()
                .map(answer -> new ChecklistAnswerResponseDTO.AnswerDto(
                        answer.getAnswerId(),
                        answer.getQuestionId().getQuestionId(),
                        answer.getLimjangId().getLimjangId(),
                        answer.getAnswer()))
                .collect(Collectors.toList());

        ChecklistAnswerAndReportResponseDTO.ReportResponseDTO reportDto = new ChecklistAnswerAndReportResponseDTO.ReportResponseDTO(
                report.getReportId(),
                report.getIndoorKeyword(),
                report.getPublicSpaceKeyword(),
                report.getLocationConditionsKeyword(),
                report.getIndoorRate(),
                report.getPublicSpaceRate(),
                report.getLocationConditionsRate(),
                report.getTotalRate());

        return new ChecklistAnswerAndReportResponseDTO(answerDtoList, reportDto);
    }

}
