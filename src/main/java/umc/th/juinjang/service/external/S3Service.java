package umc.th.juinjang.service.external;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import umc.th.juinjang.apiPayload.code.status.ErrorStatus;
import umc.th.juinjang.apiPayload.exception.handler.S3Handler;

@Slf4j
@RequiredArgsConstructor    // final 멤버변수가 있으면 생성자 항목에 포함시킴
@Component
@Service
public class S3Service {

  private final AmazonS3Client amazonS3Client;

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  // MultipartFile을 전달받아 File로 전환한 후 S3에 업로드
  public String upload(MultipartFile multipartFile, String dirName) throws IOException {
    File uploadFile = convert(multipartFile)
        .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));
    return upload(uploadFile, dirName, multipartFile.getInputStream(), multipartFile.getSize(), multipartFile.getContentType());
  }

  private String upload(File uploadFile,String dirName, InputStream inputStream, Long fileSize, String contentType) {
    String fileName = dirName + "/" + UUID.randomUUID() + uploadFile.getName();
    String uploadImageUrl = putS3(fileName, inputStream, fileSize, contentType);

    removeNewFile(uploadFile);  // 로컬에 생성된 File 삭제 (MultipartFile -> File 전환 하며 로컬에 파일 생성됨)

    return uploadImageUrl;      // 업로드된 파일의 S3 URL 주소 반환
  }

  private String putS3(String fileName, InputStream inputStream, Long fileSize, String contentType) {

    amazonS3Client.putObject(
        new PutObjectRequest(bucket, fileName, inputStream, makeObjectMetadata(fileSize, contentType))
    );

    return amazonS3Client.getUrl(bucket, fileName).toString();
  }

  private ObjectMetadata makeObjectMetadata(Long fileSize, String contentType) {
    ObjectMetadata objMeta = new ObjectMetadata();
    objMeta.setContentLength(fileSize);
    objMeta.setContentType(contentType);
    objMeta.setContentDisposition("inline");
    return objMeta;
  }

  private void removeNewFile(File targetFile) {
    if(targetFile.delete()) {
      log.info("파일이 삭제되었습니다.");
    }else {
      log.info("파일 삭제 실패");
    }
  }

  private Optional<File> convert(MultipartFile file) throws  IOException {
    String originalFilename = file.getOriginalFilename();
    String safeFilename = originalFilename.replaceAll("[^a-zA-Z0-9.-]", "_");
    File convertFile = new File(safeFilename);

    if(convertFile.createNewFile()) {
      try (FileOutputStream fos = new FileOutputStream(convertFile)) {
        fos.write(file.getBytes());
      }
      return Optional.of(convertFile);
    }
    return Optional.empty();
  }

  //파일 삭제
  public void deleteFile(String filePath) {

    try {
      URI uri = URI.create(filePath);
      String keyName = uri.getPath().substring(1);
      boolean isObjectExist = amazonS3Client.doesObjectExist(bucket, keyName);
      if (isObjectExist) {
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, keyName));
      } else {
        // s3에 해당 이미지 존재하지 않음
        throw new S3Handler(ErrorStatus.S3_NOT_FOUND);
      }
    } catch (Exception e) {
      throw new S3Handler(ErrorStatus.S3_DELTE_FAILED);
    }}

}