package umc.th.juinjang.service.ChecklistService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import umc.th.juinjang.converter.checklist.ChecklistQuestionConverter;
import umc.th.juinjang.model.dto.checklist.ChecklistQuestionDTO;
import umc.th.juinjang.model.entity.enums.ChecklistQuestionCategory;
import umc.th.juinjang.model.entity.enums.ChecklistQuestionVersion;
import umc.th.juinjang.repository.checklist.ChecklistQuestionRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChecklistQueryServiceImpl implements ChecklistQueryService {

    private final ChecklistQuestionRepository checklistQuestionRepository;

    @Override
    public List<ChecklistQuestionDTO.QuestionDto> getChecklistQuestionListByVersion(int version) {
        System.out.println("int version : " + version);
        System.out.println("enum version : " + ChecklistQuestionVersion.find(version));
        return ChecklistQuestionConverter.toChecklistQuestionListDTO(checklistQuestionRepository.findChecklistQuestionsByVersion(ChecklistQuestionVersion.find(version)));
    }
}
