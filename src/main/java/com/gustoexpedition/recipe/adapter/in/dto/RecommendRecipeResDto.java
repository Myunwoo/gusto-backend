package com.gustoexpedition.recipe.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * packageName : com.gustoexpedition.recipe.adapter.in.dto
 * fileName : RecommendRecipeResDto
 * author : fddsg
 * date : 2026-01-16
 * description : 레시피 추천 응답 DTO
 */
@Getter
@AllArgsConstructor
@Schema(description = "레시피 추천 응답")
public class RecommendRecipeResDto {
  @Schema(description = "추천 레시피 목록 (최대 10개)")
  private List<RecipeRecommendationDto> recipes;
}
