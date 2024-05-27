package umc.th.juinjang.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import umc.th.juinjang.apiPayload.ApiResponse;
import umc.th.juinjang.model.dto.checklist.*;
import umc.th.juinjang.service.ChecklistService.ChecklistCommandService;
import umc.th.juinjang.service.ChecklistService.ChecklistQueryService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class ChecklistController {

    private final ChecklistQueryService checklistQueryService;
    private final ChecklistCommandService checklistCommandService;

//    @CrossOrigin
//    @Operation(summary = "버전별 체크리스트 질문 조회")
//    @GetMapping("/checklist")
//    public ApiResponse<List<ChecklistQuestionDTO.QuestionListDto>> getChecklistQuestion(@RequestParam Integer version){
//        return ApiResponse.onSuccess(checklistQueryService.getChecklistQuestionListByVersion(version));
//    }

    @CrossOrigin
    @Operation(summary = "체크리스트 답변 생성/수정")
    @PostMapping("/checklist/{limjangId}")
    public ApiResponse<ChecklistAnswerAndReportResponseDTO> postChecklistAnswer(@PathVariable(name="limjangId") Long limjangId, @RequestBody List<ChecklistAnswerRequestDTO.AnswerDto> answerDtos){
        return ApiResponse.onSuccess(checklistCommandService.saveChecklistAnswerList(limjangId, answerDtos));
    }

    @CrossOrigin
    @Operation(summary = "체크리스트 답변 조회")
    @GetMapping("/checklist/{limjangId}")
    public ApiResponse<List<ChecklistAnswerResponseDTO.AnswerDto>> getChecklistAnswer(@PathVariable(name="limjangId") Long limjangId){
        return ApiResponse.onSuccess(checklistQueryService.getChecklistAnswerListByLimjang(limjangId));
    }

//    @CrossOrigin
//    @Operation(summary = "체크리스트 조회")
//    @GetMapping("/checklist/{limjangId}")
//    public ApiResponse<List<ChecklistQuestionDTO.QuestionListDto>> getChecklist(@PathVariable(name="limjangId") Long limjangId){
//        return ApiResponse.onSuccess(checklistQueryService.getChecklistByLimjang(limjangId));
//    }

    @CrossOrigin
    @Operation(summary = "리포트 조회")
    @GetMapping("/report/{limjangId}")
    public ApiResponse<ReportResponseDTO> getReport(@PathVariable(name="limjangId") Long limjangId){
        return ApiResponse.onSuccess(checklistQueryService.getReportByLimjangId(limjangId));
    }

}
