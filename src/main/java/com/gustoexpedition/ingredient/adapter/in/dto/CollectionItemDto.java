package com.gustoexpedition.ingredient.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

/**
 * packageName : com.gustoexpedition.ingredient.adapter.in.dto
 * fileName : CollectionItemDto
 * author : fddsg
 * date : 2026-01-16
 * description : 컬렉션 아이템 정보 DTO
 */
@Getter
@AllArgsConstructor
@Schema(description = "컬렉션 아이템 정보")
public class CollectionItemDto {
  @Schema(description = "아이템 ID", example = "1")
  private Long collectionItemId;

  @Schema(description = "재료 ID", example = "1")
  private Long ingredientId;

  @Schema(description = "추가 순서", example = "1")
  private Integer addedOrder;

  @Schema(description = "추가 일시", example = "2026-01-16T10:30:00Z")
  private Instant createdAt;
}
