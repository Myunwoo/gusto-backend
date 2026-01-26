package com.gustoexpedition.recipe.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

/**
 * packageName : com.gustoexpedition.recipe.adapter.in.dto
 * fileName : SelectRecipeListItemDto
 * author : fddsg
 * date : 2026-01-20
 * description : 레시피 목록 조회 응답 DTO (목록용)
 */
@Getter
@AllArgsConstructor
@Schema(description = "레시피 목록 항목")
public class SelectRecipeListItemDto {
  @Schema(description = "레시피 ID", example = "1")
  private Long recipeId;

  @Schema(description = "레시피 제목", example = "토마토 파스타")
  private String title;

  @Schema(description = "생성 일시", example = "2026-01-16T10:30:00Z")
  private Instant createdAt;

  @Schema(description = "수정 일시", example = "2026-01-16T10:30:00Z")
  private Instant updatedAt;
}
