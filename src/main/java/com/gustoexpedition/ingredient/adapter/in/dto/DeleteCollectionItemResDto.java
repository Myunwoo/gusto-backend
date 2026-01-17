package com.gustoexpedition.ingredient.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * packageName : com.gustoexpedition.ingredient.adapter.in.dto
 * fileName : DeleteCollectionItemResDto
 * author : fddsg
 * date : 2026-01-16
 * description : 컬렉션 아이템 삭제 응답 DTO
 */
@Getter
@AllArgsConstructor
@Schema(description = "컬렉션 아이템 삭제 응답")
public class DeleteCollectionItemResDto {
  @Schema(description = "삭제된 아이템 ID", example = "1")
  private Long collectionItemId;

  @Schema(description = "삭제 성공 메시지", example = "컬렉션 아이템이 성공적으로 삭제되었습니다.")
  private String message;
}
