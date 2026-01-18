package com.gustoexpedition.recipe.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * packageName : com.gustoexpedition.recipe.adapter.in.dto
 * fileName : MissingIngredientDto
 * author : fddsg
 * date : 2026-01-16
 * description : 부족한 재료 정보 DTO
 */
@Getter
@AllArgsConstructor
@Schema(description = "부족한 재료 정보")
public class MissingIngredientDto {
  @Schema(description = "재료 ID", example = "5")
  private Long ingredientId;

  @Schema(description = "재료명", example = "토마토")
  private String ingredientName;
}
