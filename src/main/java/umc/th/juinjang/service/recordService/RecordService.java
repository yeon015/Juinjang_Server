package umc.th.juinjang.service.recordService;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import umc.th.juinjang.apiPayload.ExceptionHandler;
import umc.th.juinjang.apiPayload.code.status.ErrorStatus;
import umc.th.juinjang.converter.record.RecordConverter;
import umc.th.juinjang.model.dto.record.RecordRequestDTO;
import umc.th.juinjang.model.entity.Record;
import umc.th.juinjang.repository.record.RecordRepository;

import java.io.IOException;
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

    public String uploadRecord(RecordRequestDTO.RecordDto recordRequestDTO, MultipartFile multipartFile) throws IOException {
        if(multipartFile != null) {
            String originalFilename = multipartFile.getOriginalFilename();
            String newfileName = UUID.randomUUID()+"_"+originalFilename;

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(multipartFile.getSize());
            metadata.setContentType(multipartFile.getContentType());

//            System.out.println(originalFilename);

            //S3에 저장
            amazonS3Client.putObject(bucket, "record/"+newfileName, multipartFile.getInputStream(), metadata);
            //DB에 저장

            Record record = RecordConverter.toEntity(recordRequestDTO, newfileName);
            recordRepository.save(record);

            return newfileName;
        }
        else{
            throw new ExceptionHandler(ErrorStatus._BAD_REQUEST);
        }
    }


}
