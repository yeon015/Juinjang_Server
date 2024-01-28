package umc.th.juinjang.service.ChecklistService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import umc.th.juinjang.apiPayload.code.status.ErrorStatus;
import umc.th.juinjang.apiPayload.exception.handler.ChecklistHandler;
import umc.th.juinjang.apiPayload.exception.handler.LimjangHandler;
import umc.th.juinjang.converter.checklist.ChecklistAnswerAndReportConverter;
import umc.th.juinjang.converter.checklist.ChecklistAnswerConverter;
import umc.th.juinjang.model.dto.checklist.ChecklistAnswerAndReportResponseDTO;
import umc.th.juinjang.model.dto.checklist.ChecklistAnswerRequestDTO;
import umc.th.juinjang.model.dto.checklist.ChecklistAnswerResponseDTO;
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
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChecklistCommandServiceImpl implements ChecklistCommandService{
    private final ChecklistAnswerRepository checklistAnswerRepository;
    private final ChecklistQuestionRepository checklistQuestionRepository;
    private final LimjangRepository limjangRepository;
    private final ReportRepository reportRepository;

    @Transactional
    public ChecklistAnswerAndReportResponseDTO saveChecklistAnswerList(Long limjangId, List<ChecklistAnswerRequestDTO.AnswerDto> answerDtoList) {
        Limjang limjang = limjangRepository.findById(limjangId)
                .orElseThrow(() -> new LimjangHandler(ErrorStatus.LIMJANG_NOTFOUND_ERROR));
        if (!checklistAnswerRepository.findChecklistAnswerByLimjangId(limjang).isEmpty()) {
            checklistAnswerRepository.deleteAllByLimjangId(limjang);
        }

        List<ChecklistAnswer> answerList = createAnswerList(limjang, answerDtoList);
        answerList = checklistAnswerRepository.saveAll(answerList);

        //ChecklistQuestion의 Category로 그룹을 지어줌
        //ategorizedAnswers는 ChecklistQuestionCategory를 키로, 해당 카테고리에 해당하는 ChecklistAnswer 리스트를 값으로 가지는 Map
        Map<ChecklistQuestionCategory, List<ChecklistAnswer>> categorizedAnswers = answerList.stream()
                .collect(Collectors.groupingBy(answer -> answer.getQuestionId().getCategory()));

        Report report = findOrCreateReport(limjang);
        reportRepository.save(calculateAndSetCategoryRates(report, categorizedAnswers));
        System.out.println("****" + report.getIndoorRate().toString() + " " + report.getPublicSpaceRate().toString() + " "+ report.getLocationConditionsRate().toString());
        System.out.println(report.getIndoorKeyword() + " " + report.getPublicSpaceKeyword() + " "+ report.getLocationConditionsKeyword());

        return ChecklistAnswerAndReportConverter.toDto(answerList, report, limjang);
    }

    private List<ChecklistAnswer> createAnswerList(Limjang limjang, List<ChecklistAnswerRequestDTO.AnswerDto> answerDtoList) {
        return answerDtoList.stream()
                .map(dto -> {
                    ChecklistQuestion question = checklistQuestionRepository.findById(dto.getQuestionId())
                            .orElseThrow(() -> new ChecklistHandler(ErrorStatus.CHECKLIST_NOTFOUND_ERROR));

                    return ChecklistAnswer.builder()
                            .questionId(question)
                            .limjangId(limjang)
                            .answer(dto.getAnswer())
                            .build();
                })
                .collect(Collectors.toList());
    }

    private Report findOrCreateReport(Limjang limjang) {
        return reportRepository.findByLimjangId(limjang)
                .orElseGet(() -> Report.builder()
                        .limjangId(limjang)
                        .build());
    }

    private Report calculateAndSetCategoryRates(Report report, Map<ChecklistQuestionCategory, List<ChecklistAnswer>> categorizedAnswers) {
        float totalRate = 0;
        int categoryCount = categorizedAnswers.size();

        ChecklistQuestionCategory[] categories = ChecklistQuestionCategory.values();
        for (ChecklistQuestionCategory category : categories) {
            List<ChecklistAnswer> answers = categorizedAnswers.get(category);
            Float categoryRate = calculateAverage(answers);
            String keyword = setRandomKeyword(categoryRate);

            System.out.println(categoryRate);
            System.out.println(keyword);

            switch (category) {
                case INDOOR:
                    report.setIndoorRate(categoryRate);
                    report.setIndoorKeyword(keyword);
                    System.out.println("indoor");
                    break;
                case PUBLIC_SPACE:
                    report.setPublicSpaceRate(categoryRate);
                    report.setPublicSpaceKeyword(keyword);
                    System.out.println("public space");
                    break;
                case LOCATION_CONDITION:
                    report.setLocationConditionsRate(categoryRate);
                    report.setLocationConditionsKeyword(keyword);
                    System.out.println("location condition");
                    break;
            }

            totalRate += categoryRate;
        }
        System.out.println("####" + report.getIndoorRate().toString() + " " + report.getPublicSpaceRate().toString() + " "+ report.getLocationConditionsRate().toString());
        System.out.println(report.getIndoorKeyword() + " " + report.getPublicSpaceKeyword() + " "+ report.getLocationConditionsKeyword());
        report.setTotalRate(totalRate / categoryCount);
        return report;
    }

    private Float calculateAverage(List<ChecklistAnswer> answers) {
        Float total = 0f;
        int count = 0;
        if (answers != null) {
            for (ChecklistAnswer answer : answers) {
                if (answer.getQuestionId().getAnswerType() == ChecklistQuestionType.INT) {
                    total += Float.parseFloat(answer.getAnswer());
                    count++;
                }
            }
            return total / count;
        }
        return total;
    }

    public String setRandomKeyword(Float rate){

        String[] oneToTwo = { "불안한" ,"불안정한" ,"불쾌한"};
        String[] twoToThree = {"평균적인", "보통의", "나쁘지 않은"};
        String[] threeToFour = {"좋은", "좋은 편인", "훌륭한", "쾌적한"};
        String[] fourToFive = {"최상의", "최고의", "상당히 좋은", "상당히 쾌적한"};
        String defaultKeyword = "아직 미평가된";

        Random random = new Random();

        if (rate >= 1 && rate < 2) {
            return oneToTwo[random.nextInt(oneToTwo.length)];
        } else if (rate >= 2 && rate < 3) {
            return twoToThree[random.nextInt(twoToThree.length)];
        } else if (rate >= 3 && rate < 4) {
            return threeToFour[random.nextInt(threeToFour.length)];
        } else if (rate >= 4 && rate <= 5) {
            return fourToFive[random.nextInt(fourToFive.length)];
        }

        return defaultKeyword;

    }
}
