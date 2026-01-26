package com.gustoexpedition.recipe.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * packageName : com.gustoexpedition.recipe.adapter.in.dto
 * fileName : RecipeLocaleInfoDto
 * author : fddsg
 * date : 2026-01-20
 * description : 레시피 Locale별 정보 DTO
 */
@Getter
@AllArgsConstructor
@Schema(description = "레시피 Locale별 정보")
public class RecipeLocaleInfoDto {
  @Schema(description = "레시피 설명", example = "간단하고 맛있는 토마토 파스타입니다.")
  private String description;

  @Schema(description = "조리 방법", example = "1. 파스타를 끓는 물에 넣고 10분간 삶는다.")
  private String instructions;
}
