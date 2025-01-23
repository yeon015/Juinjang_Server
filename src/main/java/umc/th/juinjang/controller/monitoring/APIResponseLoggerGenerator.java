package umc.th.juinjang.controller.monitoring;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.springframework.web.util.ContentCachingResponseWrapper;

public class APIResponseLoggerGenerator extends APILoggerGenerator {
  private final ContentCachingResponseWrapper responseWrapper;
  public APIResponseLoggerGenerator(ContentCachingResponseWrapper responseWrapper) {
    this.responseWrapper = responseWrapper;
  }

  @Override
  public String generateLog() {
    Map<String, Object> logData = getBaseLogInfo();

    logData.put("status", responseWrapper.getStatus());
    logData.put("responseBody", getBody(responseWrapper.getContentAsByteArray()));

    return getJsonToString(logData);
  }

  protected String getBody(byte[] info) {
    String responseBody = new String(info, StandardCharsets.UTF_8);

    responseBody = responseBody.replaceAll("\"accessToken\":\"[^\"]*\"", "\"accessToken\":\"[TOKEN]\"");
    responseBody = responseBody.replaceAll("\"refreshToken\":\"[^\"]*\"", "\"refreshToken\":\"[TOKEN]\"");
    try {
      Object jsonObject = objectMapper.readValue(responseBody, Object.class);
      return objectMapper.writeValueAsString(jsonObject);
    } catch (Exception e){
      logger.info("토큰 파싱 중 오류 발");
    }
    return null;
  }
}
