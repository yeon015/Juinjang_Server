package umc.th.juinjang.controller.monitoring;

import static umc.th.juinjang.model.common.LoggerProvider.getLogger;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;
import umc.th.juinjang.apiPayload.code.status.ErrorStatus;

@Component
@Slf4j
public class APILoggerFilter extends OncePerRequestFilter {
  private final APILoggerPrinter apiLoggerPrinter = new APILoggerPrinter();
  private static final Logger logger = getLogger(APILoggerFilter.class);

  @Override
  protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain chain ) throws IOException {
    MDC.put("request_id", UUID.randomUUID().toString());

    CustomContentCachingHttpRequestWrapper requestWrapper =  new CustomContentCachingHttpRequestWrapper(servletRequest);
    ContentCachingResponseWrapper responseWrapper =  new ContentCachingResponseWrapper(servletResponse);;

    try {
      apiLoggerPrinter.print(new APIRequestLoggerGenerator(requestWrapper));
      chain.doFilter(requestWrapper, responseWrapper);
      apiLoggerPrinter.print(new APIResponseLoggerGenerator(responseWrapper));
    } catch (Exception e) {
      logger.error("APILogger 필터 오류");
    } finally {
      responseWrapper.copyBodyToResponse();
      MDC.clear();
    }

  }
}
