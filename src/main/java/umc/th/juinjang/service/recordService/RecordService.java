package umc.th.juinjang.service.recordService;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
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

    public RecordResponseDTO.RecordDto uploadRecord(RecordRequestDTO.RecordDto recordRequestDTO, MultipartFile multipartFile) throws IOException {
        System.out.println(recordRequestDTO.getRecordScript());
        System.out.println(recordRequestDTO.getLimjangId());
        if(limjangRepository.findById(recordRequestDTO.getLimjangId()).isEmpty()){
            throw new ExceptionHandler(ErrorStatus.LIMJANG_NOTFOUND_ERROR);
        }
        Limjang limjang = limjangRepository.findById(recordRequestDTO.getLimjangId()).get();
        String recordName = "새로운 녹음 "+String.valueOf(limjang.getRecordCount()+1);

        limjangRepository.incrementRecordCount(limjang.getLimjangId());

        if(multipartFile != null) {
            String originalFilename = multipartFile.getOriginalFilename();
            String newfileName = UUID.randomUUID()+"_"+originalFilename;

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(multipartFile.getSize());
            metadata.setContentType(multipartFile.getContentType());


            //S3에 저장
            amazonS3Client.putObject(bucket, "record/"+newfileName, multipartFile.getInputStream(), metadata);

            //DB에 저장
            Record record = RecordConverter.toEntity(recordRequestDTO, amazonS3Client.getUrl(bucket, "record/"+newfileName).toString(), limjang, recordName);
            Record saveRecord = recordRepository.save(record);

            return RecordConverter.toDto(record);

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
            String keyName = record.getRecordUrl().replace(defaultUrl+"/", "");
            log.info("Record deleted: {}", keyName);

            boolean isObjectExist = amazonS3Client.doesObjectExist(bucket, keyName);

            log.info("Record isObjectExist: {}", isObjectExist);
            if (!isObjectExist) {
                throw new FileNotFoundException();
            } else {
                //S3에서 삭제
                amazonS3Client.deleteObject(bucket, keyName);

                //db에서 삭제
                recordRepository.deleteById(recordId);


                log.info("Record deleted: {}", recordId);  // 추가
            }

        } catch (Exception e) {
//            throw new Exception(e);
        }
        log.info("Record deleted: {}", recordId);  // 추가
        return "삭제 성공했습니다.";
    }

    public List<RecordResponseDTO.RecordDto> getAllRecord(Long limjangId) {
        Limjang limjang = limjangRepository.findById(limjangId)
                .orElseThrow(() -> new LimjangHandler(ErrorStatus.LIMJANG_NOTFOUND_ERROR));

        List<Record> records = recordRepository.findAllByLimjangIdOrderByRecordIdDesc(limjang);

        return RecordConverter.toDtoList(records);

    }

    public List<RecordResponseDTO.RecordDto> getThreeRecord(Long limjangId) {
        Limjang limjang = limjangRepository.findById(limjangId)
                .orElseThrow(() -> new LimjangHandler(ErrorStatus.LIMJANG_NOTFOUND_ERROR));

        List<Record> records = recordRepository.findTop3ByLimjangIdOrderByRecordIdDesc(limjang);

        return RecordConverter.toDtoList(records);
        //파일 가져오는 부분 공부하고 올게요 총총,,,

    }
}
