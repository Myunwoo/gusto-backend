package com.gustoexpedition.common.annotation;

import com.gustoexpedition.common.validator.LocaleValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * packageName    : com.gustoexpedition.common.annotation
 * fileName       : ValidLocale
 * author         : fddsg
 * date           : 2026-01-16
 * description    : locale 필드 검증 어노테이션
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LocaleValidator.class)
public @interface ValidLocale {
    String message() default "LOCALE001";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
