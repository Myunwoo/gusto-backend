package com.gustoexpedition.common.util;

import com.gustoexpedition.common.annotation.XssSafe;
import org.springframework.stereotype.Component;

/**
 * packageName    : com.gustoexpedition.common.util
 * fileName       : InputSanitizer
 * author         : fddsg
 * date           : 2026-01-16
 * description    : 입력 데이터 Sanitization 유틸리티
 */
@Component
public class InputSanitizer {

    /**
     * XSS 방어를 위한 기본 Sanitization
     * HTML 태그, JavaScript 코드, 이벤트 핸들러 제거
     */
    public String sanitize(String input) {
        return sanitize(input, true, true, true);
    }

    /**
     * XSS 방어를 위한 Sanitization (옵션 지정 가능)
     * 
     * @param input 입력 문자열
     * @param removeHtmlTags HTML 태그 제거 여부
     * @param removeJavaScript JavaScript 코드 제거 여부
     * @param removeEventHandlers 이벤트 핸들러 제거 여부
     * @return Sanitized 문자열
     */
    public String sanitize(String input, boolean removeHtmlTags, 
                          boolean removeJavaScript, boolean removeEventHandlers) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        String result = input;

        // HTML 태그 제거
        if (removeHtmlTags) {
            result = result.replaceAll("<[^>]*>", "");
        }

        // JavaScript 코드 제거
        if (removeJavaScript) {
            result = result.replaceAll("(?i)javascript:", "");
            result = result.replaceAll("(?i)vbscript:", "");
            result = result.replaceAll("(?i)onload=", "");
        }

        // 이벤트 핸들러 제거 (onclick=, onerror=, onmouseover= 등)
        if (removeEventHandlers) {
            result = result.replaceAll("(?i)on\\w+\\s*=", "");
        }

        // 추가 보안: SQL Injection 시도 패턴 제거 (방어적 코딩)
        result = result.replaceAll("(?i)(union|select|insert|update|delete|drop|exec|execute)\\s+", "");

        return result.trim();
    }

    /**
     * @XssSafe annotation 기반 Sanitization
     */
    public String sanitize(String input, XssSafe annotation) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        if (annotation == null) {
            return sanitize(input);
        }

        return sanitize(input, 
                       annotation.removeHtmlTags(), 
                       annotation.removeJavaScript(), 
                       annotation.removeEventHandlers());
    }

    /**
     * URL 검증 및 Sanitization
     * HTML 태그는 제거하지 않고 URL 형식만 검증
     */
    public String sanitizeUrl(String url) {
        if (url == null || url.isEmpty()) {
            return url;
        }

        // 기본적인 URL 형식 검증
        String trimmed = url.trim();
        
        // http:// 또는 https://로 시작하는지 확인
        if (!trimmed.matches("^https?://.*")) {
            throw new IllegalArgumentException("INVALID_URL");
        }

        // JavaScript 코드 제거 (javascript: 프로토콜 방어)
        trimmed = trimmed.replaceAll("(?i)javascript:", "");
        
        // 이벤트 핸들러 제거
        trimmed = trimmed.replaceAll("(?i)on\\w+\\s*=", "");

        return trimmed;
    }

    /**
     * 설명 필드용 Sanitization (HTML 태그는 보존, JavaScript만 제거)
     * HTML 태그가 포함된 설명을 저장해야 할 때 사용
     */
    public String sanitizeDescription(String description) {
        if (description == null || description.isEmpty()) {
            return description;
        }

        String result = description;

        // JavaScript 코드만 제거 (HTML 태그는 보존)
        result = result.replaceAll("(?i)javascript:", "");
        result = result.replaceAll("(?i)vbscript:", "");
        
        // 이벤트 핸들러 제거
        result = result.replaceAll("(?i)on\\w+\\s*=", "");

        return result.trim();
    }
}
