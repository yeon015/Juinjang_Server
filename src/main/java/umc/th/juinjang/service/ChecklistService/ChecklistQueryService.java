package umc.th.juinjang.service.ChecklistService;

import umc.th.juinjang.model.dto.checklist.ChecklistQuestionDTO;

import java.util.List;

public interface ChecklistQueryService {
    List<ChecklistQuestionDTO.QuestionDto> getChecklistQuestionListByVersion(int version);

}
