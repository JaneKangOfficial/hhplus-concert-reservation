package io.hhplus.concert.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<ErrorFilter> loggingFilter() {
        FilterRegistrationBean<ErrorFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new ErrorFilter());
        registrationBean.addUrlPatterns("/*"); // 모든 URL 패턴에 대해 필터 적용

        return registrationBean;
    }
}
