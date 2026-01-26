package com.gustoexpedition.recipe.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * packageName : com.gustoexpedition.recipe.entity
 * fileName : RecipeI18nId
 * author : fddsg
 * date : 2026-01-20
 * description : 레시피 다국어 정보 복합 키
 */
public class RecipeI18nId implements Serializable {

  private Long recipeId;
  private String locale;

  public RecipeI18nId() {
  }

  public RecipeI18nId(Long recipeId, String locale) {
    this.recipeId = recipeId;
    this.locale = locale;
  }

  public Long getRecipeId() {
    return recipeId;
  }

  public void setRecipeId(Long recipeId) {
    this.recipeId = recipeId;
  }

  public String getLocale() {
    return locale;
  }

  public void setLocale(String locale) {
    this.locale = locale;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    RecipeI18nId that = (RecipeI18nId) o;
    return Objects.equals(recipeId, that.recipeId) && Objects.equals(locale, that.locale);
  }

  @Override
  public int hashCode() {
    return Objects.hash(recipeId, locale);
  }
}
