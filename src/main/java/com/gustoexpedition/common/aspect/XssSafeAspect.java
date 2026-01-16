package com.gustoexpedition.common.aspect;

import com.gustoexpedition.common.annotation.XssSafe;
import com.gustoexpedition.common.util.InputSanitizer;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * packageName    : com.gustoexpedition.common.aspect
 * fileName       : XssSafeAspect
 * author         : fddsg
 * date           : 2026-01-16
 * description    : @XssSafe annotation이 붙은 필드 자동 Sanitization (AOP 방식)
 * 
 * 사용법:
 * 1. DTO 필드에 @XssSafe annotation 추가
 * 2. Controller에서 @Valid @RequestBody 사용
 * 3. 자동으로 Sanitization 적용됨
 * 
 * 예시:
 * public class CreateIngredientBasicReqDto {
 *     @XssSafe
 *     private String name;  // 자동으로 HTML 태그 제거됨
 * }
 */
@Aspect
@Component
@RequiredArgsConstructor
public class XssSafeAspect {

    private final InputSanitizer inputSanitizer;

    /**
     * @RequestBody가 붙은 파라미터를 가로채서 @XssSafe 필드 Sanitize
     */
    @Around("execution(* com.gustoexpedition..*Controller.*(.., @org.springframework.web.bind.annotation.RequestBody (*), ..))")
    public Object sanitizeRequestBody(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        
        // 모든 파라미터를 순회하며 @RequestBody DTO 찾기
        for (Object arg : args) {
            if (arg != null && isDtoObject(arg)) {
                sanitizeFields(arg);
            }
        }
        
        return joinPoint.proceed(args);
    }

    /**
     * DTO 객체인지 확인 (간단한 휴리스틱)
     */
    private boolean isDtoObject(Object arg) {
        String className = arg.getClass().getSimpleName();
        String packageName = arg.getClass().getPackageName();
        
        // DTO 패턴 확인
        return className.endsWith("Dto") || 
               className.endsWith("ReqDto") || 
               className.endsWith("ResDto") ||
               packageName.contains("dto");
    }

    /**
     * 객체의 모든 필드를 순회하며 @XssSafe가 붙은 String 필드 Sanitize
     */
    private void sanitizeFields(Object target) {
        Class<?> clazz = target.getClass();
        
        // 상속된 필드도 포함하여 처리
        while (clazz != null && clazz != Object.class) {
            for (Field field : clazz.getDeclaredFields()) {
                XssSafe annotation = field.getAnnotation(XssSafe.class);
                
                if (annotation != null && field.getType() == String.class) {
                    field.setAccessible(true);
                    try {
                        String value = (String) field.get(target);
                        if (value != null) {
                            String sanitized = inputSanitizer.sanitize(value, annotation);
                            field.set(target, sanitized);
                        }
                    } catch (IllegalAccessException e) {
                        // 필드 접근 실패 시 로그만 남기고 계속 진행
                        // (final 필드 등 접근 불가능한 경우)
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
    }
}
