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

  @Column(name = "description", columnDefinition = "TEXT")
  private String description;

  @Column(name = "instructions", columnDefinition = "TEXT")
  private String instructions;

  @Column(name = "servings")
  private Integer servings;

  @Column(name = "cook_time_minutes")
  private Integer cookTimeMinutes;

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

  public RecipeEntity(String title, String description, String instructions, Integer servings,
      Integer cookTimeMinutes) {
    this.title = title;
    this.description = description;
    this.instructions = instructions;
    this.servings = servings;
    this.cookTimeMinutes = cookTimeMinutes;
    this.requiredIngredientIds = new Integer[0];
    this.optionalIngredientIds = new Integer[0];
  }

  public Long getRecipeId() {
    return recipeId;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public String getInstructions() {
    return instructions;
  }

  public Integer getServings() {
    return servings;
  }

  public Integer getCookTimeMinutes() {
    return cookTimeMinutes;
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

  public void setDescription(String description) {
    this.description = description;
  }

  public void setInstructions(String instructions) {
    this.instructions = instructions;
  }

  public void setServings(Integer servings) {
    this.servings = servings;
  }

  public void setCookTimeMinutes(Integer cookTimeMinutes) {
    this.cookTimeMinutes = cookTimeMinutes;
  }

  public void setRequiredIngredientIds(Integer[] requiredIngredientIds) {
    this.requiredIngredientIds = requiredIngredientIds != null ? requiredIngredientIds : new Integer[0];
  }

  public void setOptionalIngredientIds(Integer[] optionalIngredientIds) {
    this.optionalIngredientIds = optionalIngredientIds != null ? optionalIngredientIds : new Integer[0];
  }
}
