package com.gustoexpedition.ingredient.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * packageName : com.gustoexpedition.ingredient.adapter.in.dto
 * fileName : UpdateCollectionItemOrderResDto
 * author : fddsg
 * date : 2026-01-16
 * description : 컬렉션 아이템 순서 수정 응답 DTO
 */
@Getter
@AllArgsConstructor
@Schema(description = "컬렉션 아이템 순서 수정 응답")
public class UpdateCollectionItemOrderResDto {
  @Schema(description = "아이템 ID", example = "1")
  private Long collectionItemId;

  @Schema(description = "컬렉션 ID", example = "1")
  private Long collectionId;

  @Schema(description = "재료 ID", example = "1")
  private Long ingredientId;

  @Schema(description = "수정된 순서", example = "2")
  private Integer addedOrder;
}
