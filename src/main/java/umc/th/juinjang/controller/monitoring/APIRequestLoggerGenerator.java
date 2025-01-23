package umc.th.juinjang.controller.monitoring;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class APIRequestLoggerGenerator extends APILoggerGenerator{

  private final CustomContentCachingHttpRequestWrapper requestWrapper;

  public APIRequestLoggerGenerator(CustomContentCachingHttpRequestWrapper requestWrapper) {
    this.requestWrapper = requestWrapper;
  }

  @Override
  public String generateLog() {
    Map<String, Object> logData = getBaseLogInfo();

    logData.put("method", requestWrapper.getMethod());
    logData.put("uri", requestWrapper.getRequestURI());
    logData.put("query", requestWrapper.getQueryString());
    logData.put("headers", getHeaders(requestWrapper));
    logData.put("requestBody", requestWrapper.getRequestBody());

    return getJsonToString(logData);
  }

  private Map<String, String> getHeaders(HttpServletRequest request) {
    return Collections.list(request.getHeaderNames()).stream()
        .filter(headerName -> !headerName.equalsIgnoreCase("Authorization"))
        .collect(Collectors.toMap(
            headerName -> headerName,
            request::getHeader
        ));
  }
}
