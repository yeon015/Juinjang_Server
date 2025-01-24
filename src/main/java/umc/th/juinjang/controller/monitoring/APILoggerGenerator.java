package umc.th.juinjang.controller.monitoring;

import static umc.th.juinjang.utils.LoggerProvider.getLogger;

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

  abstract protected String getBody(byte[] info);
}
