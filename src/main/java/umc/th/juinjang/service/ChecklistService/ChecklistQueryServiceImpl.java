package umc.th.juinjang.service.ChecklistService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import umc.th.juinjang.apiPayload.code.status.ErrorStatus;
import umc.th.juinjang.apiPayload.exception.handler.LimjangHandler;
import umc.th.juinjang.converter.checklist.ChecklistAnswerConverter;
import umc.th.juinjang.converter.checklist.ChecklistQuestionConverter;
import umc.th.juinjang.model.dto.checklist.ChecklistAnswerResponseDTO;
import umc.th.juinjang.model.dto.checklist.ChecklistQuestionDTO;
import umc.th.juinjang.model.entity.ChecklistAnswer;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.enums.ChecklistQuestionCategory;
import umc.th.juinjang.model.entity.enums.ChecklistQuestionVersion;
import umc.th.juinjang.repository.checklist.ChecklistAnswerRepository;
import umc.th.juinjang.repository.checklist.ChecklistQuestionRepository;
import umc.th.juinjang.repository.limjang.LimjangRepository;
import umc.th.juinjang.service.LimjangService.LimjangQueryService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChecklistQueryServiceImpl implements ChecklistQueryService {

    private final ChecklistQuestionRepository checklistQuestionRepository;
    private final ChecklistAnswerRepository checklistAnswerRepository;
    private final LimjangRepository limjangRepository;

    @Override
    public List<ChecklistQuestionDTO.QuestionDto> getChecklistQuestionListByVersion(int version) {
        System.out.println("int version : " + version);
        System.out.println("enum version : " + ChecklistQuestionVersion.find(version));
        return ChecklistQuestionConverter.toChecklistQuestionListDTO(checklistQuestionRepository.findChecklistQuestionsByVersion(ChecklistQuestionVersion.find(version)));
    }
    public List<ChecklistAnswerResponseDTO.AnswerDto> getChecklistAnswerListByLimjang(Long limjangId) {
        Limjang limjang = limjangRepository.findById(limjangId)
                .orElseThrow(() -> new LimjangHandler(ErrorStatus.LIMJANG_NOTFOUND_ERROR));
        List<ChecklistAnswer> answerList = checklistAnswerRepository.findChecklistAnswerByLimjangId(limjang);
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
