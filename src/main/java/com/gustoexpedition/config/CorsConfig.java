package com.gustoexpedition.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

/**
 * packageName : com.gustoexpedition.config
 * fileName : CorsConfig
 * author : fddsg
 * date : 2026-01-18
 * description : CORS 설정
 * 
 * 환경별로 다른 CORS 정책 적용
 */
@Configuration
@ConfigurationProperties(prefix = "gusto.cors")
@Getter
@Setter
public class CorsConfig {

  // 허용할 origin 목록 (환경별로 설정)
  private List<String> allowedOrigins = List.of();

  // 허용할 HTTP 메서드
  private List<String> allowedMethods = Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS");

  // 허용할 헤더
  private List<String> allowedHeaders = Arrays.asList("*");

  // 노출할 헤더
  private List<String> exposedHeaders = Arrays.asList("Authorization", "Content-Type");

  // 자격 증명 허용 여부
  private boolean allowCredentials = true;

  // Preflight 요청 캐시 시간 (초)
  private long maxAge = 3600;

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();

    // Origin 설정: 설정 파일의 값을 사용
    // CORS 스펙: credentials를 사용하려면 와일드카드(*)가 아닌 구체적인 origin이어야 함
    if (allowedOrigins.isEmpty()) {
      // 설정이 없으면 모든 origin 허용 (credentials 사용 불가)
      config.setAllowedOriginPatterns(List.of("*"));
      config.setAllowCredentials(false);
    } else {
      // 설정된 origin만 허용 (credentials 사용 가능)
      config.setAllowedOrigins(allowedOrigins);
      config.setAllowCredentials(allowCredentials);
    }

    config.setAllowedMethods(allowedMethods);
    config.setAllowedHeaders(allowedHeaders);
    config.setExposedHeaders(exposedHeaders);
    config.setMaxAge(maxAge);

    source.registerCorsConfiguration("/**", config);
    return source;
  }

  @Bean
  public CorsFilter corsFilter() {
    return new CorsFilter(corsConfigurationSource());
  }
}
