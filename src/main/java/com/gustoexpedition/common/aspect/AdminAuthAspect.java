package com.gustoexpedition.common.aspect;

import com.gustoexpedition.common.annotation.RequireAdmin;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

/**
 * packageName : com.gustoexpedition.common.aspect
 * fileName : AdminAuthAspect
 * author : fddsg
 * date : 2026-01-16
 * description : @RequireAdmin 어노테이션이 붙은 메서드에 대해 어드민 권한을 검증하는 AOP
 * 
 * 동작 방식:
 * 1. @RequireAdmin이 붙은 메서드 호출 시 이 AOP가 실행됨
 * 2. AccessToken에서 사용자 정보를 추출
 * 3. 사용자가 어드민 권한을 가지고 있는지 확인
 * 4. 어드민이 아니면 AUTH003 예외 발생
 */
@Slf4j
@Aspect
@Component
@Order(1) // AccessTokenFilter 이후에 실행되도록 설정
public class AdminAuthAspect {

  @Before("@annotation(requireAdmin) || @within(requireAdmin)")
  public void checkAdminAuth(JoinPoint joinPoint, RequireAdmin requireAdmin) {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
        .getRequest();

    // TODO: 현재는 JWT가 적용되어 있지 않으므로 검증을 건너뜀, 아래 주석과 같은 코드가 필요
    // AccessToken에서 사용자 정보 추출 (AccessTokenFilter에서 이미 검증됨)
    // String accessToken = extractAccessToken(request);
    log.debug("어드민 권한 검증 건너뛰기 (JWT 미적용): {}", request.getRequestURI());
    return;

    // TODO: JWT 서비스 구현 후 아래 코드 활성화
    // String accessToken = extractAccessToken(request);
    // if (accessToken == null) {
    // log.warn("어드민 권한 검증 실패: AccessToken이 없습니다. {}", request.getRequestURI());
    // throw new GustoException(requireAdmin.message());
    // }
    //
    // UserInfo userInfo = jwtService.extractUserInfo(accessToken);
    // if (!userInfo.isAdmin()) {
    // log.warn("어드민 권한 검증 실패: 사용자 ID={}, URI={}", userInfo.getUserId(),
    // request.getRequestURI());
    // throw new GustoException(requireAdmin.message());
    // }
    //
    // log.debug("어드민 권한 검증 통과: {}", request.getRequestURI());
  }
}
