package com.zerock.apiserver.config;

import com.zerock.apiserver.security.handler.APILoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@Log4j2
@RequiredArgsConstructor
public class CustomSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        log.info("---------------------- SECURITY CONFIG ----------------------");

        //CORS 설정을 등록
        http.cors(httpSecurityCorsConfigurer -> {
            httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource());
        });

        // JWT에서(API에서) 토큰은 [무상태] 여야함
        // 세션을 만들지않게 설정
        http.sessionManagement(httpSecuritySessionManagementConfigurer -> {
            httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.NEVER);
        });

        // CSRF 비활성화
        // JWT쓸 땐 보통 CSRF 안씀
        http.csrf(httpSecurityCsrfConfigurer -> {
            httpSecurityCsrfConfigurer.disable();
        });

        // 로그인 api주소
        http.formLogin(confing -> {
            confing.loginPage("/api/member/login");
            confing.successHandler(new APILoginSuccessHandler());
        });

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // CORS 관련 설정
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOriginPatterns(Arrays.asList("*")); // 모든 출처(origin / 도메인)에서 들어오는 요청을 허용
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")); // 클라이언트에서 허용할 HTTP 메서드를 명시
        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type")); // 클라이언트 요청 헤더 중에서 서버가 허용할 헤더 리스트를 명시함.
        corsConfiguration.setAllowCredentials(true); // 자격 증명(쿠키, 인증 헤더 등)을 포함한 요청을 허용하는 설정입니다.
        // ex) 브라우저가 인증정보(예: 쿠키)를 서로 주고받는 것을 가능하게 하여, 로그인된 세션 유지 같은 인증 상황에 중요합니다.

        // 모든 URL 경로("/**")에 대해 위에서 정의한 CORS 설정을 적용하여, 모든 요청에 CORS 정책이 일괄 적용되도록 설정
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}
