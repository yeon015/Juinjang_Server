package umc.th.juinjang.controller.monitoring;

import static umc.th.juinjang.model.common.LoggerProvider.getLogger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.antlr.v4.runtime.misc.MultiMap;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.web.util.ContentCachingResponseWrapper;

public abstract class APILoggerGenerator {
  protected static final Logger logger = getLogger(APILoggerGenerator.class);
  protected final Map<String, Object> logInfo = new HashMap<>();
  protected static ObjectMapper objectMapper = new ObjectMapper();

  public APILoggerGenerator() {
    logInfo.put("request-id", MDC.get("request_id"));
    logInfo.put("user-id", MDC.get("user-id"));
  }

  abstract public String generateLog();

  protected String getJsonToString(Map<String, Object> logData) {
    try {
      return objectMapper.writeValueAsString(logData);
    } catch (Exception e) {
      logger.error("로그 생성 에러", e);
      return null;
    }
  }

  protected Map<String, Object> getBaseLogInfo() {
    return new HashMap<>(logInfo);
  }
}
