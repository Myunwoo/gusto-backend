package com.gustoexpedition.recipe.entity;

import com.gustoexpedition.common.entity.UpdatedAtListener;
import jakarta.persistence.*;

import java.time.Instant;

/**
 * packageName : com.gustoexpedition.recipe.entity
 * fileName : RecipeI18nEntity
 * author : fddsg
 * date : 2026-01-20
 * description : 레시피 다국어 정보 엔티티
 */
@Entity
@Table(name = "recipe_i18n")
@EntityListeners(UpdatedAtListener.class)
@IdClass(RecipeI18nId.class)
public class RecipeI18nEntity {

  @Id
  @Column(name = "recipe_id", nullable = false)
  private Long recipeId;

  @Id
  @Column(name = "locale", nullable = false, length = 10)
  private String locale;

  @Column(name = "description", columnDefinition = "TEXT")
  private String description;

  @Column(name = "instructions", columnDefinition = "TEXT")
  private String instructions;

  @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
  private Instant createdAt;

  @Column(name = "updated_at", nullable = false, insertable = false, updatable = true)
  private Instant updatedAt;

  protected RecipeI18nEntity() {
  }

  public RecipeI18nEntity(Long recipeId, String locale, String description, String instructions) {
    this.recipeId = recipeId;
    this.locale = locale;
    this.description = description;
    this.instructions = instructions;
  }

  public Long getRecipeId() {
    return recipeId;
  }

  public String getLocale() {
    return locale;
  }

  public String getDescription() {
    return description;
  }

  public String getInstructions() {
    return instructions;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setInstructions(String instructions) {
    this.instructions = instructions;
  }
}
