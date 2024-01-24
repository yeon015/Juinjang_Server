package umc.th.juinjang.service.ChecklistService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import umc.th.juinjang.converter.checklist.ChecklistAnswerConverter;
import umc.th.juinjang.model.dto.checklist.ChecklistAnswerRequestDTO;
import umc.th.juinjang.model.dto.checklist.ChecklistAnswerResponseDTO;
import umc.th.juinjang.model.entity.ChecklistAnswer;
import umc.th.juinjang.model.entity.ChecklistQuestion;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.repository.checklist.ChecklistAnswerRepository;
import umc.th.juinjang.repository.checklist.ChecklistQuestionRepository;
import umc.th.juinjang.repository.limjang.LimjangRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChecklistCommandServiceImpl implements ChecklistCommandService{
    private final ChecklistAnswerRepository checklistAnswerRepository;
    private final ChecklistQuestionRepository checklistQuestionRepository;
    private final LimjangRepository limjangRepository;

    public List<ChecklistAnswerResponseDTO.AnswerDto> saveChecklistAnswerList(Long limjangId, List<ChecklistAnswerRequestDTO.AnswerDto> answerDtoList) {
        List<ChecklistAnswer> answerList = answerDtoList.stream()
                .map(dto -> {
                    ChecklistQuestion question = checklistQuestionRepository.findById(dto.getQuestionId())
                            .orElseThrow(() -> new IllegalArgumentException("Question not found: " + dto.getQuestionId()));
                    Limjang limjang = limjangRepository.findById(limjangId)
                            .orElseThrow(() -> new IllegalArgumentException("Limjang not found: " + limjangId));

                    return ChecklistAnswer.builder()
                            .questionId(question)
                            .limjangId(limjang)
                            .answer(dto.getAnswer())
                            .build();
                })
                .collect(Collectors.toList());

        answerList = checklistAnswerRepository.saveAll(answerList);

        return answerList.stream()
                .map(entity -> ChecklistAnswerResponseDTO.AnswerDto.builder()
                        .answerId(entity.getAnswerId())
                        .questionId(entity.getQuestionId().getQuestionId())
                        .limjangId(entity.getLimjangId().getLimjangId())
                        .answer(entity.getAnswer())
                        .build())
                .collect(Collectors.toList());
    }
}
