package com.gustoexpedition.recipe.entity;

import com.gustoexpedition.common.entity.UpdatedAtListener;
import jakarta.persistence.*;

import java.time.Instant;

/**
 * packageName : com.gustoexpedition.recipe.entity
 * fileName : RecipeEntity
 * author : fddsg
 * date : 2026-01-16
 * description : 레시피 엔티티
 */
@Entity
@Table(name = "recipe")
@EntityListeners(UpdatedAtListener.class)
public class RecipeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "recipe_id")
  private Long recipeId;

  @Column(name = "title", nullable = false, length = 120)
  private String title;

  @Column(name = "source", length = 500)
  private String source;

  @Column(name = "required_ingredient_ids", nullable = false, columnDefinition = "int[]")
  private Integer[] requiredIngredientIds = new Integer[0];

  @Column(name = "optional_ingredient_ids", nullable = false, columnDefinition = "int[]")
  private Integer[] optionalIngredientIds = new Integer[0];

  @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
  private Instant createdAt;

  @Column(name = "updated_at", nullable = false, insertable = false, updatable = true)
  private Instant updatedAt;

  protected RecipeEntity() {
  }

  public RecipeEntity(String title) {
    this.title = title;
    this.requiredIngredientIds = new Integer[0];
    this.optionalIngredientIds = new Integer[0];
  }

  public RecipeEntity(String title, String source) {
    this.title = title;
    this.source = source;
    this.requiredIngredientIds = new Integer[0];
    this.optionalIngredientIds = new Integer[0];
  }

  public Long getRecipeId() {
    return recipeId;
  }

  public String getTitle() {
    return title;
  }

  public String getSource() {
    return source;
  }

  public Integer[] getRequiredIngredientIds() {
    return requiredIngredientIds;
  }

  public Integer[] getOptionalIngredientIds() {
    return optionalIngredientIds;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public void setRequiredIngredientIds(Integer[] requiredIngredientIds) {
    this.requiredIngredientIds = requiredIngredientIds != null ? requiredIngredientIds : new Integer[0];
  }

  public void setOptionalIngredientIds(Integer[] optionalIngredientIds) {
    this.optionalIngredientIds = optionalIngredientIds != null ? optionalIngredientIds : new Integer[0];
  }
}
