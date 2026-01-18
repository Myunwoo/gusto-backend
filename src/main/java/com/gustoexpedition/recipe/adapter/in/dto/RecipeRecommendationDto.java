package com.gustoexpedition.recipe.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * packageName : com.gustoexpedition.recipe.adapter.in.dto
 * fileName : RecipeRecommendationDto
 * author : fddsg
 * date : 2026-01-16
 * description : 추천 레시피 정보 DTO (우선순위 메타데이터 포함)
 */
@Getter
@AllArgsConstructor
@Schema(description = "추천 레시피 정보")
public class RecipeRecommendationDto {
  @Schema(description = "레시피 ID", example = "1")
  private Long recipeId;

  @Schema(description = "레시피 제목", example = "토마토 파스타")
  private String title;

  @Schema(description = "레시피 설명", example = "간단하고 맛있는 토마토 파스타입니다.")
  private String description;

  @Schema(description = "우선순위 점수 (높을수록 추천도 높음)", example = "85")
  private Integer priorityScore;

  @Schema(description = "필수 재료 중 부족한 재료 목록 (ID와 이름 포함)")
  private List<MissingIngredientDto> missingRequiredIngredients;

  @Schema(description = "옵셔널 재료 중 부족한 재료 목록 (ID와 이름 포함)")
  private List<MissingIngredientDto> missingOptionalIngredients;

  @Schema(description = "필수 재료 매칭률 (0-100)", example = "80")
  private Integer requiredMatchRate;

  @Schema(description = "옵셔널 재료 매칭률 (0-100)", example = "50")
  private Integer optionalMatchRate;
}
