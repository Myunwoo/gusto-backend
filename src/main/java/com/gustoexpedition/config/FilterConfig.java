package com.gustoexpedition.config;

import com.gustoexpedition.common.filter.AccessTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * packageName    : com.gustoexpedition.config
 * fileName       : FilterConfig
 * author         : fddsg
 * date           : 2026-01-15
 * description    : Filter 설정 클래스
 * 
 * Spring Security 도입 시:
 * - 이 클래스를 제거하고 SecurityConfig에서 SecurityFilterChain으로 필터 등록
 * - AccessTokenFilter는 OncePerRequestFilter를 상속하므로 코드 변경 없이 사용 가능
 */
@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    private final AccessTokenFilter accessTokenFilter;

    @Bean
    public FilterRegistrationBean<AccessTokenFilter> accessTokenFilterRegistration() {
        FilterRegistrationBean<AccessTokenFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(accessTokenFilter);
        registration.addUrlPatterns("/*");
        registration.setOrder(1); // 필터 실행 순서
        registration.setName("accessTokenFilter");
        return registration;
    }
}
