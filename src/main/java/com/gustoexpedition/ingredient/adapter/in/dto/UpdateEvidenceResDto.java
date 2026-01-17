package com.gustoexpedition.ingredient.adapter.in.dto;

import com.gustoexpedition.ingredient.entity.IngredientEdgeEvidenceEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

/**
 * packageName : com.gustoexpedition.ingredient.adapter.in.dto
 * fileName : UpdateEvidenceResDto
 * author : fddsg
 * date : 2026-01-16
 * description : 재료 간 관계 증거 수정 응답 DTO
 */
@Getter
@AllArgsConstructor
@Schema(description = "재료 간 관계 증거 수정 응답")
public class UpdateEvidenceResDto {
  @Schema(description = "증거 ID", example = "1")
  private Long evidenceId;

  @Schema(description = "관계 ID", example = "1")
  private Long edgeId;

  @Schema(description = "증거 타입", example = "BOOK")
  private IngredientEdgeEvidenceEntity.EdgeEvidenceType evidenceType;

  @Schema(description = "제목", example = "요리 백과사전")
  private String title;

  @Schema(description = "내용", example = "토마토와 바질의 조합에 대한 설명...")
  private String content;

  @Schema(description = "출처 참조", example = "https://example.com/reference")
  private String sourceRef;

  @Schema(description = "생성 일시", example = "2026-01-16T10:30:00Z")
  private Instant createdAt;
}
