package umc.th.juinjang.controller.monitoring;

import java.nio.charset.StandardCharsets;
import org.springframework.web.util.ContentCachingResponseWrapper;

public class APIResponseLoggerGenerator extends APILoggerGenerator {
  private final ContentCachingResponseWrapper response;

  public APIResponseLoggerGenerator(ContentCachingResponseWrapper response) {
    this.response = response;
  }

  @Override
  public String generateLog() {
    StringBuilder logBuilder = new StringBuilder();

    logBuilder.append("Response : ").append(" ");
    logBuilder.append(getBaseLogInfo());
    logBuilder.append("[status] ").append(response.getStatus()).append(" ");
    logBuilder.append("[responseBody] ").append(getBody(response.getContentAsByteArray())).append(" ");

    return logBuilder.toString();
  }

  protected String getBody(byte[] info) {
    String responseBody = new String(info, StandardCharsets.UTF_8);
    responseBody = responseBody.replaceAll("\"accessToken\":\"[^\"]*\"", "\"accessToken\":\"[TOKEN]\"");
    responseBody = responseBody.replaceAll("\"refreshToken\":\"[^\"]*\"", "\"refreshToken\":\"[TOKEN]\"");
    return  responseBody;
  }
}
