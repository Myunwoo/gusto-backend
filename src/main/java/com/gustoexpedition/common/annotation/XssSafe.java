package com.gustoexpedition.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * packageName    : com.gustoexpedition.common.annotation
 * fileName       : XssSafe
 * author         : fddsg
 * date           : 2026-01-16
 * description    : XSS 방어를 위한 입력 Sanitization 어노테이션
 * 
 * 사용법:
 * @XssSafe
 * private String name;
 * 
 * 이 어노테이션이 붙은 필드는 자동으로 HTML 태그와 JavaScript 코드가 제거됨됨
 * 
 * 주의사항:
 * - URL 필드에 적용 불가가 (URL 검증이 필요합니다)
 * - HTML 태그가 포함된 설명 필드에 적용 불가 (데이터 손실 가능)
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface XssSafe {
    /**
     * HTML 태그 제거 여부 (기본값: true)
     */
    boolean removeHtmlTags() default true;
    
    /**
     * JavaScript 코드 제거 여부 (기본값: true)
     */
    boolean removeJavaScript() default true;
    
    /**
     * 이벤트 핸들러 제거 여부 (onclick=, onerror= 등, 기본값: true)
     */
    boolean removeEventHandlers() default true;
}
