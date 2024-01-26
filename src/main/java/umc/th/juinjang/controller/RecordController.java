package umc.th.juinjang.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.th.juinjang.apiPayload.ApiResponse;
import umc.th.juinjang.model.dto.record.RecordRequestDTO;
import umc.th.juinjang.model.dto.record.RecordResponseDTO;
import umc.th.juinjang.service.recordService.RecordService;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/record")
@RequiredArgsConstructor
public class RecordController {

    @Autowired
    private RecordService recordService;



    //등록
    @PostMapping()
    public ApiResponse<String> uploadRecord(
            @RequestPart(name = "file", required = true) MultipartFile file,
            @RequestPart RecordRequestDTO.RecordDto recordRequestDTO) {
        try{
            String fileUrl = recordService.uploadRecord(recordRequestDTO, file);
            return ApiResponse.onSuccess(fileUrl);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{recordId}")
    public ApiResponse<String> deleteRecord(@PathVariable Long recordId){
        try{
            String result = recordService.deleteRecord(recordId);
            return ApiResponse.onSuccess(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/all/{limjang_id}")
    public ApiResponse <List<RecordRequestDTO.RecordDto>> getAllRecord(@PathVariable Long recordId){
        try{
            List<RecordRequestDTO.RecordDto> recordList = recordService.getAllRecord(recordId);
            return ApiResponse.onSuccess(recordList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}