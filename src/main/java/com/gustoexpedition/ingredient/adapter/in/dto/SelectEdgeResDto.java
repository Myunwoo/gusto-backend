package com.gustoexpedition.ingredient.adapter.in.dto;

import com.gustoexpedition.ingredient.entity.IngredientEdgeEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * packageName : com.gustoexpedition.ingredient.adapter.in.dto
 * fileName : SelectEdgeResDto
 * author : fddsg
 * date : 2026-01-16
 * description : 재료 간 관계 조회 응답 DTO
 */
@Getter
@AllArgsConstructor
@Schema(description = "재료 간 관계 조회 응답")
public class SelectEdgeResDto {
  @Schema(description = "관계 ID", example = "1")
  private Long edgeId;

  @Schema(description = "첫 번째 재료 ID", example = "1")
  private Long fromIngredientId;

  @Schema(description = "두 번째 재료 ID", example = "2")
  private Long toIngredientId;

  @Schema(description = "관계 타입", example = "PAIR_WELL")
  private IngredientEdgeEntity.IngredientRelationType relationType;

  @Schema(description = "관계 점수 (1-10)", example = "5")
  private Integer score;

  @Schema(description = "신뢰도", example = "0.850")
  private BigDecimal confidence;

  @Schema(description = "관계 요약", example = "토마토와 바질은 클래식한 조합입니다.")
  private String reasonSummary;

  @Schema(description = "증거 목록")
  private List<EvidenceDto> evidences;

  @Schema(description = "생성 일시", example = "2026-01-16T10:30:00Z")
  private Instant createdAt;

  @Schema(description = "수정 일시", example = "2026-01-16T10:30:00Z")
  private Instant updatedAt;
}
