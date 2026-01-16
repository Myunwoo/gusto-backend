package com.gustoexpedition.ingredient.adapter.in.dto.annotation;

import com.gustoexpedition.ingredient.adapter.in.dto.validator.AliasesValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * packageName    : com.gustoexpedition.ingredient.adapter.in.dto.annotation
 * fileName       : ValidAliases
 * author         : fddsg
 * date           : 2026-01-16
 * description    : aliases Map의 key(locale) 검증 어노테이션
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AliasesValidator.class)
public @interface ValidAliases {
    String message() default "LOCALE001";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
