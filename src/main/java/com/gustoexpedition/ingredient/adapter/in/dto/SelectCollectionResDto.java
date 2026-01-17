package com.gustoexpedition.ingredient.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

/**
 * packageName : com.gustoexpedition.ingredient.adapter.in.dto
 * fileName : SelectCollectionResDto
 * author : fddsg
 * date : 2026-01-16
 * description : 재료 컬렉션 조회 응답 DTO
 */
@Getter
@AllArgsConstructor
@Schema(description = "재료 컬렉션 조회 응답")
public class SelectCollectionResDto {
  @Schema(description = "컬렉션 ID", example = "1")
  private Long collectionId;

  @Schema(description = "컬렉션 제목", example = "내가 좋아하는 재료")
  private String title;

  @Schema(description = "컬렉션 설명", example = "요리에 자주 사용하는 재료들을 모았습니다.")
  private String note;

  @Schema(description = "포함된 재료 목록")
  private List<CollectionItemDto> items;

  @Schema(description = "생성 일시", example = "2026-01-16T10:30:00Z")
  private Instant createdAt;

  @Schema(description = "수정 일시", example = "2026-01-16T10:30:00Z")
  private Instant updatedAt;
}
