package com.gustoexpedition.common.aspect;

import com.gustoexpedition.common.annotation.RequireAdmin;
import com.gustoexpedition.common.exception.GustoException;
import com.gustoexpedition.user.domain.UserInfo;
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

    // AccessTokenFilter에서 이미 검증하고 Request Attribute에 저장한 사용자 정보 가져오기
    UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");

    if (userInfo == null) {
      log.warn("어드민 권한 검증 실패: 사용자 정보가 없습니다. {}", request.getRequestURI());
      throw new GustoException("AUTH003");
    }

    // 활성화 여부 확인
    if (!userInfo.getIsActive()) {
      log.warn("어드민 권한 검증 실패: 비활성화된 사용자. userNum={}, URI={}", userInfo.getUserNum(),
          request.getRequestURI());
      throw new GustoException("AUTH003");
    }

    // 어드민 권한 확인
    if (!userInfo.isAdmin()) {
      log.warn("어드민 권한 검증 실패: 사용자 userNum={}, role={}, URI={}", userInfo.getUserNum(),
          userInfo.getRole(), request.getRequestURI());
      throw new GustoException("AUTH003");
    }

    log.debug("어드민 권한 검증 통과: userNum={}, URI={}", userInfo.getUserNum(), request.getRequestURI());
  }
}
