package umc.th.juinjang.service.ChecklistService;

import umc.th.juinjang.model.dto.checklist.ChecklistAnswerResponseDTO;
import umc.th.juinjang.model.dto.checklist.ChecklistQuestionDTO;
import umc.th.juinjang.model.dto.checklist.ReportResponseDTO;

import java.util.List;

public interface ChecklistQueryService {
    List<ChecklistQuestionDTO.QuestionDto> getChecklistQuestionListByVersion(int version);

    public List<ChecklistAnswerResponseDTO.AnswerDto> getChecklistAnswerListByLimjang(Long limjangId);

    public ReportResponseDTO getReportByLimjangId(Long limjangId);

}
