package com.gustoexpedition.recipe.adapter.in.dto;

import com.gustoexpedition.recipe.entity.RecipeIngredientRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * packageName : com.gustoexpedition.recipe.adapter.in.dto
 * fileName : UpdateRecipeIngredientResDto
 * author : fddsg
 * date : 2026-01-16
 * description : 레시피 재료 수정 응답 DTO
 */
@Getter
@AllArgsConstructor
@Schema(description = "레시피 재료 수정 응답")
public class UpdateRecipeIngredientResDto {
  @Schema(description = "레시피 재료 ID", example = "1")
  private Long recipeIngredientId;

  @Schema(description = "레시피 ID", example = "1")
  private Long recipeId;

  @Schema(description = "재료 ID", example = "1")
  private Long ingredientId;

  @Schema(description = "역할", example = "REQUIRED")
  private RecipeIngredientRole role;

  @Schema(description = "양", example = "120.000")
  private BigDecimal amount;

  @Schema(description = "단위", example = "g")
  private String unit;

  @Schema(description = "비고", example = "얇게 썬 것")
  private String note;
}
