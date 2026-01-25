package com.gustoexpedition.ingredient.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * packageName : com.gustoexpedition.ingredient.adapter.in.dto
 * fileName : SelectIngredientListItemDto
 * author : fddsg
 * date : 2026-01-20
 * description : 재료 목록 조회 응답 DTO (목록용)
 */
@Getter
@AllArgsConstructor
@Schema(description = "재료 목록 항목")
public class SelectIngredientListItemDto {
  @Schema(description = "재료 ID", example = "1")
  private Long ingredientId;

  @Schema(description = "재료 기본명 (ingredient 테이블의 name 컬럼)", example = "토마토")
  private String name;

  @Schema(description = "활성화 여부", example = "true")
  private Boolean isActive;
}
