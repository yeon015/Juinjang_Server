package umc.th.juinjang.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import umc.th.juinjang.apiPayload.ApiResponse;
import umc.th.juinjang.model.dto.checklist.ChecklistAnswerRequestDTO;
import umc.th.juinjang.model.dto.checklist.ChecklistAnswerResponseDTO;
import umc.th.juinjang.model.dto.checklist.ChecklistQuestionDTO;
import umc.th.juinjang.service.ChecklistService.ChecklistCommandService;
import umc.th.juinjang.service.ChecklistService.ChecklistQueryService;

import java.util.List;

@RestController
@RequestMapping("/api/checklist")
@RequiredArgsConstructor
@Validated
public class ChecklistController {

    private final ChecklistQueryService checklistQueryService;
    private final ChecklistCommandService checklistCommandService;

    @CrossOrigin
    @Operation(summary = "버전별 체크리스트 질문 조회")
    @GetMapping("")
    public ApiResponse<List<ChecklistQuestionDTO.QuestionDto>> getChecklistQuestion(@RequestParam Integer version){
        return ApiResponse.onSuccess(checklistQueryService.getChecklistQuestionListByVersion(version));
    }

    @CrossOrigin
    @Operation(summary = "체크리스트 답변 생성/수정")
    @PostMapping("/{limjangId}")
    public ApiResponse<List<ChecklistAnswerResponseDTO.AnswerDto>> postChecklistAnswer(@PathVariable Long limjangId, @RequestBody List<ChecklistAnswerRequestDTO.AnswerDto> answerDtos){
        return ApiResponse.onSuccess(checklistCommandService.saveChecklistAnswerList(limjangId, answerDtos));
    }

    @CrossOrigin
    @Operation(summary = "체크리스트 답변 조회")
    @GetMapping("/{limjangId}")
    public ApiResponse<List<ChecklistAnswerResponseDTO.AnswerDto>> getChecklistAnswer(@PathVariable Long limjangId){
        return ApiResponse.onSuccess(checklistQueryService.getChecklistAnswerListByLimjang(limjangId));
    }


}
