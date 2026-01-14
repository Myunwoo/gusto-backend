package com.gustoexpedition.ingredient.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * packageName    : com.gustoexpedition.ingredient.entity
 * fileName       : IngredientI18nId
 * author         : fddsg
 * date           : 2026-01-14
 * description    : IngredientI18nEntity의 복합 키 클래스
 */
public class IngredientI18nId implements Serializable {
    private Long ingredientId;
    private String locale;

    public IngredientI18nId() {}

    public IngredientI18nId(Long ingredientId, String locale) {
        this.ingredientId = ingredientId;
        this.locale = locale;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IngredientI18nId that = (IngredientI18nId) o;
        return Objects.equals(ingredientId, that.ingredientId) &&
                Objects.equals(locale, that.locale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredientId, locale);
    }
}
