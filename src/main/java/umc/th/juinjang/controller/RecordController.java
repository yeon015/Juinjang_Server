package umc.th.juinjang.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.th.juinjang.apiPayload.ApiResponse;
import umc.th.juinjang.model.dto.record.RecordRequestDTO;
import umc.th.juinjang.service.recordService.RecordService;

import java.io.IOException;

@RestController
@RequestMapping("/api/record")
@RequiredArgsConstructor
public class RecordController {

    @Autowired
    private RecordService recordService;

    //등록
    @PostMapping( consumes = {"multipart/form-data"})
    public ApiResponse<String> uploadRecord(@RequestBody RecordRequestDTO.RecordDto recordRequestDTO, @RequestPart MultipartFile file){
        try{
            String fileUrl = recordService.uploadRecord(recordRequestDTO, file);
            return ApiResponse.onSuccess(fileUrl);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
