package umc.th.juinjang.service.ChecklistService;

import umc.th.juinjang.model.dto.checklist.ChecklistAnswerRequestDTO;
import umc.th.juinjang.model.dto.checklist.ChecklistAnswerResponseDTO;

import java.util.List;

public interface ChecklistCommandService {
    public List<ChecklistAnswerResponseDTO.AnswerDto> saveChecklistAnswerList(Long limjangId, List<ChecklistAnswerRequestDTO.AnswerDto> answerDtoList);
}
