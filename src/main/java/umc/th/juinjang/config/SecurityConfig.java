package umc.th.juinjang.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import umc.th.juinjang.jwt.JwtAuthenticationFilter;
import umc.th.juinjang.jwt.JwtExceptionFilter;
import umc.th.juinjang.service.JwtService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtService jwtService;

//    private final JwtExceptionFilter jwtExceptionFilter;

    @Bean
    @Order(0)
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring()
                // 로그인 개발 끝나면 삭제 **
                .requestMatchers("/**", "/swagger-ui/**", "/swagger/**", "/swagger-resources/**", "/swagger-ui.html",
                        "/configuration/ui",  "/v3/api-docs/**", "/h2-console/**");
    }

    //선언 방식이 3.x에서 바뀜
    @Bean AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception
    { return authConfiguration.getAuthenticationManager(); }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(Customizer.withDefaults())
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                        세션을 사용하지 않는다고 설정함
                )
                .addFilter(new JwtAuthenticationFilter(authenticationManager(authenticationConfiguration),jwtService))
//                 JwtAuthenticationFilter를 필터에 넣음
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers(
                                        AntPathRequestMatcher.antMatcher("/auth/**")
                                ).authenticated()
                                .requestMatchers(
                                        AntPathRequestMatcher.antMatcher("/h2-console/**")
                                ).permitAll()

                                .anyRequest().authenticated()

                )
                .headers(
                        headersConfigurer ->
                                headersConfigurer
                                        .frameOptions(
                                                HeadersConfigurer.FrameOptionsConfig::sameOrigin
                                        )
                )
                .addFilterBefore(new JwtExceptionFilter(), JwtAuthenticationFilter.class);

        return http.build();
    }

}

