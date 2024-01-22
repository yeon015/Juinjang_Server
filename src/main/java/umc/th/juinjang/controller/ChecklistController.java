package umc.th.juinjang.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import umc.th.juinjang.apiPayload.ApiResponse;
import umc.th.juinjang.model.dto.checklist.ChecklistQuestionDTO;
import umc.th.juinjang.service.ChecklistService.ChecklistQueryService;

import java.util.List;

@RestController
@RequestMapping("/api/checklist")
@RequiredArgsConstructor
@Validated
public class ChecklistController {

    private final ChecklistQueryService checklistQueryService;

    @CrossOrigin
    @Operation(summary = "버전별 체크리스트 질문 조회")
    @GetMapping("")
    public ApiResponse<List<ChecklistQuestionDTO.QuestionDto>> getChecklistQuestion(@RequestParam Integer version){
        return ApiResponse.onSuccess(checklistQueryService.getChecklistQuestionListByVersion(version));
    }

}
