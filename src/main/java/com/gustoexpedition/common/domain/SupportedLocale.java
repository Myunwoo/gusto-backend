package com.gustoexpedition.common.domain;

/**
 * packageName    : com.gustoexpedition.common.domain
 * fileName       : SupportedLocale
 * author         : fddsg
 * date           : 2026-01-16
 * description    : 지원하는 Locale 목록 (공통 도메인)
 */
public enum SupportedLocale {
    KOREA("ko-KR", "한국"),
    JAPAN("ja-JP", "일본"),
    FRANCE("fr-FR", "프랑스"),
    ITALY("it-IT", "이탈리아"),
    USA("en-US", "미국");

    private final String code;
    private final String countryName;

    SupportedLocale(String code, String countryName) {
        this.code = code;
        this.countryName = countryName;
    }

    public String getCode() {
        return code;
    }

    public String getCountryName() {
        return countryName;
    }

    /**
     * locale 코드로 SupportedLocale 찾기
     * @param localeCode
     * @return SupportedLocale 또는 null
     */
    public static SupportedLocale fromCode(String localeCode) {
        if (localeCode == null) {
            return null;
        }
        for (SupportedLocale locale : values()) {
            if (locale.code.equals(localeCode)) {
                return locale;
            }
        }
        return null;
    }

    /**
     * locale 코드가 지원되는지 확인
     * @param localeCode locale 코드
     * @return 지원 여부
     */
    public static boolean isSupported(String localeCode) {
        return fromCode(localeCode) != null;
    }
}
