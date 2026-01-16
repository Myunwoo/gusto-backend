package com.gustoexpedition.common.validator;

import com.gustoexpedition.common.annotation.ValidLocale;
import com.gustoexpedition.common.domain.SupportedLocale;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * packageName    : com.gustoexpedition.common.validator
 * fileName       : LocaleValidator
 * author         : fddsg
 * date           : 2026-01-16
 * description    : locale 필드 검증 Validator
 */
public class LocaleValidator implements ConstraintValidator<ValidLocale, String> {

    @Override
    public boolean isValid(String locale, ConstraintValidatorContext context) {
        if (locale == null || locale.isEmpty()) {
            return false;
        }

        // 지원되는 locale인지 확인
        if (!SupportedLocale.isSupported(locale)) {
            context.disableDefaultConstraintViolation();
            // 에러 코드를 메시지로 사용 (LOCALE001:지원하지 않는 locale)
            context.buildConstraintViolationWithTemplate("LOCALE001")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
