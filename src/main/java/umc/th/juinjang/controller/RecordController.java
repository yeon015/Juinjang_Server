package umc.th.juinjang.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.th.juinjang.apiPayload.ApiResponse;
import umc.th.juinjang.model.dto.limjang.LimjangMemoResponseDTO;
import umc.th.juinjang.model.dto.record.RecordRequestDTO;
import umc.th.juinjang.model.dto.record.RecordResponseDTO;
import umc.th.juinjang.service.recordService.RecordService;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RecordController {

    @Autowired
    private RecordService recordService;



    //등록
    @PostMapping("/record")
    public  ApiResponse<RecordResponseDTO.RecordDto> uploadRecord(
            @RequestPart(name = "file", required = true) MultipartFile file,
            @RequestPart RecordRequestDTO.RecordDto recordRequestDTO) {
        try{
            RecordResponseDTO.RecordDto result = recordService.uploadRecord(recordRequestDTO, file);
            return ApiResponse.onSuccess(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/record/{recordId}")
    public ApiResponse<String> deleteRecord(@PathVariable Long recordId){
        try{
            String result = recordService.deleteRecord(recordId);
            return ApiResponse.onSuccess(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/record/all/{limjangId}")
    public ApiResponse <List<RecordResponseDTO.RecordDto>> getAllRecord(@PathVariable Long limjangId){
        try{
            List<RecordResponseDTO.RecordDto> recordList = recordService.getAllRecord(limjangId);
            return ApiResponse.onSuccess(recordList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/record/{limjangId}")
    public ApiResponse <RecordResponseDTO.RecordMemoDto> getThreeRecord(@PathVariable Long limjangId){
        try{
            RecordResponseDTO.RecordMemoDto recordMemo = recordService.getThreeRecord(limjangId);

            return ApiResponse.onSuccess(recordMemo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/memo/{limjangId}")
    public ApiResponse<LimjangMemoResponseDTO.MemoDto> createLimjangMemo(@PathVariable Long limjangId, @RequestBody String memo){
        return ApiResponse.onSuccess(recordService.createLimjangMemo(limjangId, memo));
    }

    @PatchMapping("/record/content/{recordId}")
    public ApiResponse<RecordResponseDTO.RecordDto> updateRecordContent(@PathVariable Long recordId, @RequestBody String content){
        return ApiResponse.onSuccess(recordService.updateRecordContent(recordId, content));
    }

    @PatchMapping("/record/title/{recordId}")
    public ApiResponse<RecordResponseDTO.RecordDto> updateRecordTitle(@PathVariable Long recordId, @RequestBody String title){
        return ApiResponse.onSuccess(recordService.updateRecordTitle(recordId, title));
    }
}