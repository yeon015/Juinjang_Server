package umc.th.juinjang.controller.monitoring;

import static umc.th.juinjang.utils.LoggerProvider.getLogger;
import static umc.th.juinjang.utils.LoggerProvider.registerRequestId;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
public class ApiLoggerFilter extends OncePerRequestFilter {
  private static final Logger logger = getLogger(ApiLoggerFilter.class);
  private final List<String> EXCLUDED_URLS;

  public ApiLoggerFilter(List<String> EXCLUDED_URLS) {
    this.EXCLUDED_URLS = EXCLUDED_URLS;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain chain )
      throws ServletException, IOException {
//    CustomContentCachingHttpRequestWrapper requestWrapper =  new CustomContentCachingHttpRequestWrapper(servletRequest);
//    ContentCachingResponseWrapper responseWrapper =  new ContentCachingResponseWrapper(servletResponse);
//    registerRequestId(UUID.randomUUID().toString());

    chain.doFilter(servletRequest, servletResponse);
//    try {
//      if (shouldNotFilter(requestWrapper)) {
//        chain.doFilter(requestWrapper, responseWrapper);
//        return;
//      }
//      apiLoggerPrinter.print(new APIRequestLoggerGenerator(requestWrapper));
//      chain.doFilter(requestWrapper, responseWrapper);
//      apiLoggerPrinter.print(new APIResponseLoggerGenerator(responseWrapper));
//      responseWrapper.copyBodyToResponse();
//    } catch (Exception e) {
//      logger.error("APILogger 필터 오류");
//    } finally {
//      MDC.clear();
//    }
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    AntPathMatcher pathMatcher = new AntPathMatcher();
    return EXCLUDED_URLS.stream().anyMatch(pattern -> pathMatcher.match(pattern, request.getRequestURI()));
  }
}
