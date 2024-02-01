package umc.th.juinjang.service.ChecklistService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import umc.th.juinjang.apiPayload.code.status.ErrorStatus;
import umc.th.juinjang.apiPayload.exception.handler.ChecklistHandler;
import umc.th.juinjang.apiPayload.exception.handler.LimjangHandler;
import umc.th.juinjang.converter.checklist.ChecklistAnswerAndReportConverter;
import umc.th.juinjang.model.dto.checklist.ChecklistAnswerAndReportResponseDTO;
import umc.th.juinjang.model.dto.checklist.ChecklistAnswerRequestDTO;
import umc.th.juinjang.model.entity.ChecklistAnswer;
import umc.th.juinjang.model.entity.ChecklistQuestion;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.Report;
import umc.th.juinjang.model.entity.enums.ChecklistQuestionCategory;
import umc.th.juinjang.model.entity.enums.ChecklistQuestionType;
import umc.th.juinjang.repository.checklist.ChecklistAnswerRepository;
import umc.th.juinjang.repository.checklist.ChecklistQuestionRepository;
import umc.th.juinjang.repository.checklist.ReportRepository;
import umc.th.juinjang.repository.limjang.LimjangRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChecklistCommandServiceImpl implements ChecklistCommandService {
    private final ChecklistAnswerRepository checklistAnswerRepository;
    private final ChecklistQuestionRepository checklistQuestionRepository;
    private final LimjangRepository limjangRepository;
    private final ReportRepository reportRepository;

    @Transactional
    public ChecklistAnswerAndReportResponseDTO saveChecklistAnswerList(Long limjangId, List<ChecklistAnswerRequestDTO.AnswerDto> answerDtoList) {
        Limjang limjang = getLimjang(limjangId);
        List<ChecklistAnswer> answerList = createAnswers(answerDtoList, limjang);
        Report report = createReport(answerList, limjang);

        return ChecklistAnswerAndReportConverter.toDto(answerList, report, limjang);
    }

    private Limjang getLimjang(Long limjangId) {
        return limjangRepository.findById(limjangId)
                .orElseThrow(() -> new LimjangHandler(ErrorStatus.LIMJANG_NOTFOUND_ERROR));
    }

    private List<ChecklistAnswer> createAnswers(List<ChecklistAnswerRequestDTO.AnswerDto> answerDtoList, Limjang limjang) {
        clearOldAnswersByLimjangId(limjang);
        List<ChecklistAnswer> answerList = getAnswerList(answerDtoList, limjang);
        return checklistAnswerRepository.saveAll(answerList);
    }

    private void clearOldAnswersByLimjangId(Limjang limjang) {
        if (!checklistAnswerRepository.findChecklistAnswerByLimjangId(limjang).isEmpty()) {
            checklistAnswerRepository.deleteAllByLimjangId(limjang);
        }
    }

    private List<ChecklistAnswer> getAnswerList(List<ChecklistAnswerRequestDTO.AnswerDto> answerDtoList, Limjang limjang) {
        return answerDtoList.stream()
                .map(answerDto -> ChecklistAnswer.builder()
                        .questionId(getQuestion(answerDto.getQuestionId()))
                        .limjangId(limjang)
                        .answer(answerDto.getAnswer())
                        .build())
                .collect(Collectors.toList());
    }

    private ChecklistQuestion getQuestion(Long questionId) {
        return checklistQuestionRepository.findById(questionId)
                .orElseThrow(() -> new ChecklistHandler(ErrorStatus.CHECKLIST_NOTFOUND_ERROR));
    }

    private Report createReport(List<ChecklistAnswer> answerList, Limjang limjang) {
        return reportRepository.save(getCalculatedReport(limjang, answerList));
    }

    private Report getReport(Limjang limjang) {
        return reportRepository.findByLimjangId(limjang)
                .orElseGet(() -> Report.builder()
                        .limjangId(limjang)
                        .build());
    }

    private Report getCalculatedReport(Limjang limjang, List<ChecklistAnswer> answerList) {
        Float indoorRate = getAverage(getIndoorAnswers(answerList));
        Float publicSpaceRate = getAverage(getPublicSpaceAnswers(answerList));
        Float locationConditionsRate = getAverage(getLocationConditionsAnswers(answerList));
        Float totalRate = indoorRate + publicSpaceRate + locationConditionsRate;

        Report report = getReport(limjang);
        report.setIndoorRateAndKeyword(indoorRate);
        report.setPublicSpaceRateAndKeyword(publicSpaceRate);
        report.setLocationConditionsRateAndKeyword(locationConditionsRate);
        report.setTotalRate(totalRate / getCategoryCount(answerList));
        return report;
    }

    private static List<ChecklistAnswer> getIndoorAnswers(List<ChecklistAnswer> answerList) {
        return getFilteredAnswersByCategory(answerList, ChecklistQuestionCategory.INDOOR);
    }

    private static List<ChecklistAnswer> getPublicSpaceAnswers(List<ChecklistAnswer> answerList) {
        return getFilteredAnswersByCategory(answerList, ChecklistQuestionCategory.PUBLIC_SPACE);
    }

    private static List<ChecklistAnswer> getLocationConditionsAnswers(List<ChecklistAnswer> answerList) {
        return getFilteredAnswersByCategory(answerList, ChecklistQuestionCategory.LOCATION_CONDITION);
    }

    private static List<ChecklistAnswer> getFilteredAnswersByCategory(List<ChecklistAnswer> answerList, ChecklistQuestionCategory category) {
        return answerList.stream()
                .filter(answer -> answer.getQuestionId().getCategory() == category)
                .collect(Collectors.toList());
    }

    private static int getCategoryCount(List<ChecklistAnswer> answerList) {
        return answerList.stream()
                .map(answer -> answer.getQuestionId().getCategory())
                .collect(Collectors.toSet())
                .size();
    }

    private Float getAverage(List<ChecklistAnswer> answers) {
        if (answers.isEmpty()) return 0f;
        return getTotal(answers) / getCount(answers);
    }

    private static Float getTotal(List<ChecklistAnswer> answers) {
        return answers.stream()
                .filter(answer -> answer.getQuestionId().getAnswerType() == ChecklistQuestionType.INT)
                .map(answer -> Float.parseFloat(answer.getAnswer()))
                .reduce(0f, Float::sum);
    }

    private static long getCount(List<ChecklistAnswer> answers) {
        return answers.stream()
                .filter(answer -> answer.getQuestionId().getAnswerType() == ChecklistQuestionType.INT)
                .count();
    }
}
