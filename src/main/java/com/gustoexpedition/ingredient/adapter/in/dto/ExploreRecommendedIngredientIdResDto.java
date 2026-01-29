package com.gustoexpedition.ingredient.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Explore 페이지 기본 중앙 재료 ID 응답.
 */
@Getter
@AllArgsConstructor
@Schema(description = "Explore 추천 재료 ID 응답")
public class ExploreRecommendedIngredientIdResDto {
    @Schema(description = "추천 재료 ID", example = "1")
    private Long ingredientId;
}
