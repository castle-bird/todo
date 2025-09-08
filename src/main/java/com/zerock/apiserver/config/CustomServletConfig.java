package com.zerock.apiserver.config;

import com.zerock.apiserver.controller.formatter.LocalDateFormatter;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Log4j2
public class CustomServletConfig implements WebMvcConfigurer {
    // WebMvcConfigurer를 통해 여러 config설정 가능 ex) 인터셉터 설정(요청이 컨트롤러에 가기 직전), 뷰 리졸버 설정(템플릿 처리 방법, jsp, thymeleaf) 등등
    // 그 중 addFormatters()를 통해 포메터를 설정할 수 있다
    // registry.addFormatter() 의 파라미터로는 Formatter<T>를 구현한 구현체만 작성 가능하다
    
    @Override
    public void addFormatters(FormatterRegistry registry) {
        log.info("------------------------------------------- addFormatters -------------------------------------------");
        registry.addFormatter(new LocalDateFormatter());
    }
}
