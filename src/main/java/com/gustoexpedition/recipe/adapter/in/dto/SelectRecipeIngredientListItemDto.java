package com.gustoexpedition.recipe.adapter.in.dto;

import com.gustoexpedition.recipe.entity.RecipeIngredientRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * packageName : com.gustoexpedition.recipe.adapter.in.dto
 * fileName : SelectRecipeIngredientListItemDto
 * author : fddsg
 * date : 2026-01-20
 * description : 레시피 재료 목록 항목 DTO (재료 이름 포함)
 */
@Getter
@AllArgsConstructor
@Schema(description = "레시피 재료 목록 항목")
public class SelectRecipeIngredientListItemDto {
  @Schema(description = "레시피 재료 ID", example = "1")
  private Long recipeIngredientId;

  @Schema(description = "재료 ID", example = "1")
  private Long ingredientId;

  @Schema(description = "재료 이름", example = "토마토")
  private String ingredientName;

  @Schema(description = "역할", example = "REQUIRED")
  private RecipeIngredientRole role;

  @Schema(description = "양", example = "120.000")
  private BigDecimal amount;

  @Schema(description = "단위", example = "g")
  private String unit;

  @Schema(description = "비고", example = "얇게 썬 것")
  private String note;
}
