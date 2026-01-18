package com.gustoexpedition.recipe.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * packageName : com.gustoexpedition.recipe.entity
 * fileName : RecipeIngredientEntity
 * author : fddsg
 * date : 2026-01-16
 * description : 레시피 재료 매핑 엔티티 (source of truth)
 */
@Entity
@Table(name = "recipe_ingredient", uniqueConstraints = @UniqueConstraint(name = "uk_recipe_ingredient", columnNames = {
    "recipe_id", "ingredient_id" }))
public class RecipeIngredientEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "recipe_ingredient_id")
  private Long recipeIngredientId;

  @Column(name = "recipe_id", nullable = false)
  private Long recipeId;

  @Column(name = "ingredient_id", nullable = false)
  private Long ingredientId;

  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false)
  private RecipeIngredientRole role = RecipeIngredientRole.REQUIRED;

  @Column(name = "amount", precision = 10, scale = 3)
  private BigDecimal amount;

  @Column(name = "unit", length = 30)
  private String unit;

  @Column(name = "note", length = 255)
  private String note;

  @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
  private Instant createdAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "recipe_id", insertable = false, updatable = false)
  private RecipeEntity recipe;

  protected RecipeIngredientEntity() {
  }

  public RecipeIngredientEntity(Long recipeId, Long ingredientId, RecipeIngredientRole role,
      BigDecimal amount, String unit, String note) {
    this.recipeId = recipeId;
    this.ingredientId = ingredientId;
    this.role = role != null ? role : RecipeIngredientRole.REQUIRED;
    this.amount = amount;
    this.unit = unit;
    this.note = note;
  }

  public Long getRecipeIngredientId() {
    return recipeIngredientId;
  }

  public Long getRecipeId() {
    return recipeId;
  }

  public Long getIngredientId() {
    return ingredientId;
  }

  public RecipeIngredientRole getRole() {
    return role;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public String getUnit() {
    return unit;
  }

  public String getNote() {
    return note;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public RecipeEntity getRecipe() {
    return recipe;
  }

  public void setRole(RecipeIngredientRole role) {
    this.role = role != null ? role : RecipeIngredientRole.REQUIRED;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public void setNote(String note) {
    this.note = note;
  }
}
