package com.gustoexpedition.common.filter;

import com.gustoexpedition.common.exception.GustoException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * packageName    : com.gustoexpedition.common.filter
 * fileName       : AccessTokenFilter
 * author         : fddsg
 * date           : 2026-01-15
 * description    : AccessToken 검증 필터 (화이트리스트 제외)
 * 
 * Spring Security 마이그레이션 가이드:
 * 1. OncePerRequestFilter를 상속하므로 Spring Security FilterChain에 바로 통합 가능
 * 2. FilterConfig의 FilterRegistrationBean을 제거하고 SecurityConfig에서 사용:
 * 
 *    @Configuration
 *    @EnableWebSecurity
 *    public class SecurityConfig {
 *        @Bean
 *        public SecurityFilterChain filterChain(HttpSecurity http, AccessTokenFilter accessTokenFilter) {
 *            http
 *                .addFilterBefore(accessTokenFilter, UsernamePasswordAuthenticationFilter.class)
 *                // ... 기타 설정
 *            return http.build();
 *        }
 *    }
 * 
 * 3. 화이트리스트는 SecurityConfig의 permitAll()로도 관리 가능 (선택사항)
 */
@Slf4j
@Component
@ConfigurationProperties(prefix = "gusto.security")
@Getter
@Setter
public class AccessTokenFilter extends OncePerRequestFilter {

    // AccessToken 검증 불필요 화이트리스트
    private List<String> whitelist = new ArrayList<>();
    
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private final Environment environment;
    
    // Swagger UI 경로 (개발 환경에서만 추가)
    private static final List<String> SWAGGER_UI_PATHS = Arrays.asList(
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/v3/api-docs/**",
        "/swagger-resources/**",
        "/webjars/**"
    );
    
    public AccessTokenFilter(Environment environment) {
        this.environment = environment;
    }
    
    /**
     * 실제 사용할 화이트리스트 반환 (프로파일별 Swagger UI 경로 포함)
     */
    private List<String> getEffectiveWhitelist() {
        List<String> effectiveList = new ArrayList<>(whitelist);
        
        // 개발 환경에서만 Swagger UI 경로 추가
        if (isDevProfile()) {
            effectiveList.addAll(SWAGGER_UI_PATHS);
        }
        
        return effectiveList;
    }
    
    /**
     * 개발 환경 프로파일인지 확인
     */
    private boolean isDevProfile() {
        String[] activeProfiles = environment.getActiveProfiles();
        if (activeProfiles.length == 0) {
            // 프로파일이 없으면 기본값으로 dev로 간주 (로컬 개발)
            return true;
        }
        return Arrays.asList(activeProfiles).contains("dev");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        
        String requestPath = request.getRequestURI();
        String requestMethod = request.getMethod();

        // 화이트리스트 체크
        if (isWhitelisted(requestPath, requestMethod)) {
            log.debug("화이트리스트 경로 접근: {} {}", requestMethod, requestPath);
            filterChain.doFilter(request, response);
            return;
        }

        // AccessToken 검증
        String accessToken = extractAccessToken(request);
        
        if (accessToken == null || accessToken.isEmpty()) {
            log.warn("AccessToken이 없습니다: {} {}", requestMethod, requestPath);
            throw new GustoException("AUTH001"); // AccessToken이 필요합니다
        }

        // TODO: 실제 JWT 검증 로직 구현
        if (!isValidToken(accessToken)) {
            log.warn("유효하지 않은 AccessToken: {} {}", requestMethod, requestPath);
            throw new GustoException("AUTH002"); // 유효하지 않은 AccessToken입니다
        }

        // 검증 통과
        filterChain.doFilter(request, response);
    }

    /**
     * 화이트리스트에 포함된 경로인지 확인
     * 
     * - 정적 리소스 경로(/.well-known, /favicon.ico, /robots.txt)는 GET 요청만 허용
     * - Swagger UI는 개발 환경에서만 허용
     * - Actuator 등은 모든 메서드 허용
     */
    private boolean isWhitelisted(String requestPath, String requestMethod) {
        
        List<String> effectiveWhitelist = getEffectiveWhitelist();
        
        for (String pattern : effectiveWhitelist) {
            // 패턴이 "METHOD:경로" 형식인지 확인
            if (pattern.contains(":")) {
                String[] parts = pattern.split(":", 2);
                String method = parts[0];
                String path = parts[1];
                
                if (method.equalsIgnoreCase(requestMethod) && pathMatcher.match(path, requestPath)) {
                    return true;
                }
            } else {
                // 경로만 있는 경우
                if (pathMatcher.match(pattern, requestPath)) {
                    // 보안: 정적 리소스 경로는 GET 요청만 허용
                    if (isStaticResourcePath(pattern) && !"GET".equalsIgnoreCase(requestMethod)) {
                        log.warn("정적 리소스 경로에 GET이 아닌 요청: {} {}", requestMethod, requestPath);
                        return false;
                    }
                    return true;
                }
            }
        }
        
        return false;
    }

    /**
     * 정적 리소스 경로인지 확인
     * 브라우저 자동 요청 경로는 GET만 허용
     */
    private boolean isStaticResourcePath(String pattern) {
        return pattern.startsWith("/.well-known") ||
               pattern.equals("/favicon.ico") ||
               pattern.equals("/robots.txt");
    }

    /**
     * 요청에서 AccessToken 추출
     * Authorization 헤더에서 "Bearer {token}" 형식으로 추출
     */
    private String extractAccessToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        
        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }
        
        return null;
    }

    /**
     * AccessToken 유효성 검증
     * TODO: 실제 JWT 검증 로직 구현
     */
    private boolean isValidToken(String token) {
        // TODO: JWT 검증 로직 구현
        // 예: JWT 서명 검증, 만료 시간 확인 등
        return token != null && !token.isEmpty();
    }
}
