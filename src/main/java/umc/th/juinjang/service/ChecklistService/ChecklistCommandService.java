package umc.th.juinjang.service.ChecklistService;

import umc.th.juinjang.model.dto.checklist.ChecklistAnswerAndReportResponseDTO;
import umc.th.juinjang.model.dto.checklist.ChecklistAnswerRequestDTO;
import umc.th.juinjang.model.dto.checklist.ChecklistAnswerResponseDTO;

import java.util.List;

public interface ChecklistCommandService {
    ChecklistAnswerAndReportResponseDTO saveChecklistAnswerList(Long limjangId, List<ChecklistAnswerRequestDTO.AnswerDto> answerDtoList);
}
