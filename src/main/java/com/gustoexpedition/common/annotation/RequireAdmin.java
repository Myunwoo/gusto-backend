package com.gustoexpedition.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * packageName : com.gustoexpedition.common.annotation
 * fileName : RequireAdmin
 * author : fddsg
 * date : 2026-01-16
 * description : 어드민 권한이 필요한 API를 표시하는 어노테이션
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireAdmin {
  /**
   * 에러 메시지 코드 (기본값: AUTH003)
   */
  String message() default "AUTH003";
}
