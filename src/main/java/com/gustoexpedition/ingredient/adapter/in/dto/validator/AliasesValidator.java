package com.gustoexpedition.ingredient.adapter.in.dto.validator;

import com.gustoexpedition.ingredient.adapter.in.dto.annotation.ValidAliases;
import com.gustoexpedition.common.domain.SupportedLocale;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.Map;

/**
 * packageName    : com.gustoexpedition.ingredient.adapter.in.dto.validator
 * fileName       : AliasesValidator
 * author         : fddsg
 * date           : 2026-01-16
 * description    : aliases Map의 key(locale) 검증 Validator
 */
public class AliasesValidator implements ConstraintValidator<ValidAliases, Map<String, List<String>>> {

    @Override
    public boolean isValid(Map<String, List<String>> aliases, ConstraintValidatorContext context) {
        // alias는 필수 필드가 아니므로 없을 수 있음
        if (aliases == null || aliases.isEmpty()) {
            return true;
        }

        // Map의 모든 key(locale)가 지원되는 locale인지 확인
        for (String localeCode : aliases.keySet()) {
            if (!SupportedLocale.isSupported(localeCode)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("LOCALE001")
                        .addConstraintViolation();
                return false;
            }
        }

        return true;
    }
}
