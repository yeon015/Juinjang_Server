package umc.th.juinjang.service.checklist;

import umc.th.juinjang.model.dto.checklist.ChecklistAnswerAndReportResponseDTO;
import umc.th.juinjang.model.dto.checklist.ChecklistAnswerRequestDTO;

import java.util.List;

public interface ChecklistCommandService {
    public ChecklistAnswerAndReportResponseDTO saveChecklistAnswerList(Long limjangId, List<ChecklistAnswerRequestDTO.AnswerDto> answerDtoList);
}
