package umc.th.juinjang.config;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import umc.th.juinjang.controller.monitoring.APILoggerFilter;

@Configuration
@Slf4j
public class ApiFilterConfig {
  @Bean
  public FilterRegistrationBean<APILoggerFilter> loggingFilter() {
    FilterRegistrationBean<APILoggerFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new APILoggerFilter());
    registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE); // 순서 설정
    registrationBean.setUrlPatterns(List.of("/*"));
    return registrationBean;
  }
}
