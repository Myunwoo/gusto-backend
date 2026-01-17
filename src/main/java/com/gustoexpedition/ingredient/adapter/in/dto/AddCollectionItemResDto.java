package com.gustoexpedition.ingredient.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

/**
 * packageName : com.gustoexpedition.ingredient.adapter.in.dto
 * fileName : AddCollectionItemResDto
 * author : fddsg
 * date : 2026-01-16
 * description : 컬렉션에 재료 추가 응답 DTO
 */
@Getter
@AllArgsConstructor
@Schema(description = "컬렉션에 재료 추가 응답")
public class AddCollectionItemResDto {
  @Schema(description = "아이템 ID", example = "1")
  private Long collectionItemId;

  @Schema(description = "컬렉션 ID", example = "1")
  private Long collectionId;

  @Schema(description = "재료 ID", example = "1")
  private Long ingredientId;

  @Schema(description = "추가 순서", example = "1")
  private Integer addedOrder;

  @Schema(description = "추가 일시", example = "2026-01-16T10:30:00Z")
  private Instant createdAt;
}
