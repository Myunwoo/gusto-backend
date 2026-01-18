package com.gustoexpedition.recipe.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * packageName : com.gustoexpedition.recipe.adapter.in.dto
 * fileName : RecommendRecipeReqDto
 * author : fddsg
 * date : 2026-01-16
 * description : 레시피 추천 요청 DTO
 */
@Getter
@Setter
@Schema(description = "레시피 추천 요청")
public class RecommendRecipeReqDto {

  @NotNull(message = "RECIPE010")
  @NotEmpty(message = "RECIPE010")
  @Schema(description = "사용자가 선택한 재료 ID 목록", example = "[1, 2, 3, 4]", requiredMode = Schema.RequiredMode.REQUIRED)
  private List<Long> ingredientIds;
}
