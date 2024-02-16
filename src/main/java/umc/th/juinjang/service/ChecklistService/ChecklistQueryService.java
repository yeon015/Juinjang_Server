package umc.th.juinjang.service.ChecklistService;

import umc.th.juinjang.model.dto.checklist.ChecklistAnswerResponseDTO;
import umc.th.juinjang.model.dto.checklist.ChecklistQuestionDTO;
import umc.th.juinjang.model.dto.checklist.ReportResponseDTO;

import java.util.List;

public interface ChecklistQueryService {
    public List<ChecklistQuestionDTO.QuestionListDto> getChecklistQuestionListByVersion(int version);

    public List<ChecklistAnswerResponseDTO.AnswerDto> getChecklistAnswerListByLimjang(Long limjangId);
    public List<ChecklistQuestionDTO.QuestionListDto> getChecklistByLimjang(Long limjangId);

    public ReportResponseDTO getReportByLimjangId(Long limjangId);


}
