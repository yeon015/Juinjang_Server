package umc.th.juinjang.converter.checklist;

import umc.th.juinjang.converter.limjang.LimjangDetailConverter;
import umc.th.juinjang.model.dto.checklist.ChecklistAnswerAndReportResponseDTO;
import umc.th.juinjang.model.dto.checklist.ChecklistAnswerResponseDTO;
import umc.th.juinjang.model.dto.checklist.ReportResponseDTO;
import umc.th.juinjang.model.dto.limjang.LimjangDetailResponseDTO;
import umc.th.juinjang.model.entity.ChecklistAnswer;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.Report;

import java.util.List;
import java.util.stream.Collectors;

public class ChecklistAnswerAndReportConverter {

    public static ChecklistAnswerAndReportResponseDTO toDto(List<ChecklistAnswer> answerList, Report report, Limjang limjang) {
        List<ChecklistAnswerResponseDTO.AnswerDto> answerDtoList = answerList.stream()
                .map(answer -> new ChecklistAnswerResponseDTO.AnswerDto(
                        answer.getAnswerId(),
                        answer.getQuestionId().getQuestionId(),
                        answer.getLimjangId().getLimjangId(),
                        answer.getAnswer()))
                .collect(Collectors.toList());

        ReportResponseDTO.ReportDTO reportDto = new ReportResponseDTO.ReportDTO(
                report.getReportId(),
                report.getIndoorKeyword(),
                report.getPublicSpaceKeyword(),
                report.getLocationConditionsKeyword(),
                report.getIndoorRate(),
                report.getPublicSpaceRate(),
                report.getLocationConditionsRate(),
                report.getTotalRate());

        LimjangDetailResponseDTO.DetailDto detailDto = LimjangDetailConverter.toDetail(limjang, limjang.getPriceId());

        return new ChecklistAnswerAndReportResponseDTO(answerDtoList, new ReportResponseDTO(reportDto, detailDto));
    }
    public static ReportResponseDTO toReportDto(Report report, Limjang limjang) {
        ReportResponseDTO.ReportDTO reportDTO = ReportResponseDTO.ReportDTO.builder()
                .reportId(report.getReportId())
                .indoorKeyWord(report.getIndoorKeyword())
                .publicSpaceKeyWord(report.getPublicSpaceKeyword())
                .locationConditionsWord(report.getLocationConditionsKeyword())
                .indoorRate(report.getIndoorRate())
                .publicSpaceRate(report.getPublicSpaceRate())
                .locationConditionsRate(report.getLocationConditionsRate())
                .totalRate(report.getTotalRate())
                .build();
        LimjangDetailResponseDTO.DetailDto detailDto = LimjangDetailConverter.toDetail(limjang, limjang.getPriceId());
        return new ReportResponseDTO(reportDTO, detailDto);
    }

}
