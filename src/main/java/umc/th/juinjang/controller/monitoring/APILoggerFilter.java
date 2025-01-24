package umc.th.juinjang.controller.monitoring;

import static umc.th.juinjang.utils.LoggerProvider.getLogger;
import static umc.th.juinjang.utils.LoggerProvider.registerRequestId;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
public class APILoggerFilter extends OncePerRequestFilter {
  private final APILoggerPrinter apiLoggerPrinter = new APILoggerPrinter();
  private static final Logger logger = getLogger(APILoggerFilter.class);

  @Override
  protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain chain ) {
    registerRequestId(UUID.randomUUID().toString());

    CustomContentCachingHttpRequestWrapper requestWrapper =  new CustomContentCachingHttpRequestWrapper(servletRequest);
    ContentCachingResponseWrapper responseWrapper =  new ContentCachingResponseWrapper(servletResponse);;

    try {
      apiLoggerPrinter.print(new APIRequestLoggerGenerator(requestWrapper));
      chain.doFilter(requestWrapper, responseWrapper);
      apiLoggerPrinter.print(new APIResponseLoggerGenerator(responseWrapper));
      responseWrapper.copyBodyToResponse();
    } catch (Exception e) {
      logger.error("APILogger 필터 오류");
    } finally {
      MDC.clear();
    }
  }
}
