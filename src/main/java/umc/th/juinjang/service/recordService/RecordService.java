package umc.th.juinjang.service.recordService;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import umc.th.juinjang.apiPayload.ExceptionHandler;
import umc.th.juinjang.apiPayload.code.status.ErrorStatus;
import umc.th.juinjang.apiPayload.exception.handler.LimjangHandler;
import umc.th.juinjang.converter.record.RecordConverter;
import umc.th.juinjang.model.dto.record.RecordRequestDTO;
import umc.th.juinjang.model.dto.record.RecordResponseDTO;
import umc.th.juinjang.model.entity.Limjang;
import umc.th.juinjang.model.entity.Record;
import umc.th.juinjang.repository.limjang.LimjangRepository;
import umc.th.juinjang.repository.record.RecordRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecordService {


    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.uploadPath}")
    private String defaultUrl;

    private final AmazonS3Client amazonS3Client;

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private LimjangRepository limjangRepository;

    public String uploadRecord(RecordRequestDTO.RecordDto recordRequestDTO, MultipartFile multipartFile) throws IOException {
        System.out.println(recordRequestDTO.getRecordScript());
        System.out.println(recordRequestDTO.getLimjangId());
        if(limjangRepository.findById(recordRequestDTO.getLimjangId()).isEmpty()){
            throw new ExceptionHandler(ErrorStatus.LIMJANG_NOTFOUND_ERROR);
        }
        Limjang limjang = limjangRepository.findById(recordRequestDTO.getLimjangId()).get();

        if(multipartFile != null) {
            String originalFilename = multipartFile.getOriginalFilename();
            String newfileName = UUID.randomUUID()+"_"+originalFilename;

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(multipartFile.getSize());
            metadata.setContentType(multipartFile.getContentType());

//            System.out.println(originalFilename);

            //S3에 저장
            amazonS3Client.putObject(bucket, "record/"+newfileName, multipartFile.getInputStream(), metadata);
            System.out.println(amazonS3Client.getUrl(bucket, "record/"+newfileName).toString());
            //DB에 저장

            Record record = RecordConverter.toEntity(recordRequestDTO, amazonS3Client.getUrl(bucket, "record/"+newfileName).toString(), limjang);
            Record saveRecord = recordRepository.save(record);
            return saveRecord.getRecordUrl();
        }
        else{
            throw new ExceptionHandler(ErrorStatus._BAD_REQUEST);
        }
    }


    public String deleteRecord(Long recordId) {
        //db에서 id 찾기
        if (recordRepository.findById(recordId).isEmpty()){
            throw new ExceptionHandler(ErrorStatus.RECORD_NOT_FOUND);
        }
        Record record =recordRepository.findById(recordId).get();

        try{
            String keyName = "record/"+ record.getRecordUrl();

            boolean isObjectExist = amazonS3Client.doesObjectExist(bucket, keyName);

            if (isObjectExist) {
                //S3에서 삭제
                amazonS3Client.deleteObject(bucket, keyName);
                //db에서 삭제
                recordRepository.deleteById(recordId);
            } else {
                throw new ExceptionHandler(ErrorStatus.RECORD_NOT_FOUND);
            }

        } catch (Exception e) {
//            throw new Exception(e);
        }
        return "삭제 성공했습니다.";
    }

    public List<RecordResponseDTO.RecordDto> getAllRecord(Long limjangId) {
        Limjang limjang = limjangRepository.findById(limjangId)
                .orElseThrow(() -> new LimjangHandler(ErrorStatus.LIMJANG_NOTFOUND_ERROR));

        List<Record> records = recordRepository.findAllByLimjangIdOrderByRecordIdDesc(limjang);

        return RecordConverter.toDtoList(records);
       //파일 가져오는 부분 공부하고 올게요 총총,,,

    }

    public List<RecordResponseDTO.RecordDto> getThreeRecord(Long limjangId) {
        Limjang limjang = limjangRepository.findById(limjangId)
                .orElseThrow(() -> new LimjangHandler(ErrorStatus.LIMJANG_NOTFOUND_ERROR));

        List<Record> records = recordRepository.findTop3ByLimjangIdOrderByRecordIdDesc(limjang);

        return RecordConverter.toDtoList(records);
        //파일 가져오는 부분 공부하고 올게요 총총,,,

    }
}
