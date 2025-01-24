package umc.th.juinjang.controller.monitoring;

import static umc.th.juinjang.utils.LoggerProvider.getLogger;

import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.MDC;

public abstract class APILoggerGenerator {

  protected static final Logger logger = getLogger(APILoggerGenerator.class);

  public APILoggerGenerator() {
  }

  protected StringBuilder getBaseLogInfo() {
    StringBuilder logBuilder = new StringBuilder();
    logBuilder.append("[request_id] ").append(MDC.get("request_id")).append(" ");
    return logBuilder;
  }

  abstract public String generateLog();

  protected String getBody(byte[] info) {
    return new String(info, StandardCharsets.UTF_8).replace("\n", "").replace("\r", "");  // 캐리지 리턴 제거 (운영체제별 줄바꿈 차이 대응).replace("\t", ""); // 탭 제거;
  }
}
