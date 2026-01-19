package com.gustoexpedition.ingredient.adapter.in.dto.annotation;

import com.gustoexpedition.ingredient.adapter.in.dto.validator.IngredientAliasesValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * packageName    : com.gustoexpedition.ingredient.adapter.in.dto.annotation
 * fileName       : ValidIngredientAliases
 * author         : fddsg
 * date           : 2026-01-16
 * description    : 재료(ingredient) 별칭 aliases Map의 key(locale) 검증 어노테이션
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IngredientAliasesValidator.class)
public @interface ValidIngredientAliases {
    String message() default "LOCALE001";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
