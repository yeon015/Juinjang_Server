package umc.th.juinjang.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.th.juinjang.apiPayload.ApiResponse;
import umc.th.juinjang.service.RecordService.RecordService;

import java.io.IOException;

@RestController
@RequestMapping("/api/record")
@RequiredArgsConstructor
public class RecordController {

    @Autowired
    private RecordService recordService;

    //등록
    @PostMapping("")
    public ApiResponse<String> uploadRecord(@RequestPart MultipartFile file){
        try{
            String fileUrl = recordService.saveFile(file);
            return ApiResponse.onSuccess(fileUrl);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
