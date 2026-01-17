package com.gustoexpedition.ingredient.adapter.in.dto;

import com.gustoexpedition.ingredient.entity.IngredientEdgeEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * packageName : com.gustoexpedition.ingredient.adapter.in.dto
 * fileName : UpdateEdgeResDto
 * author : fddsg
 * date : 2026-01-16
 * description : 재료 간 관계 수정 응답 DTO
 */
@Getter
@AllArgsConstructor
@Schema(description = "재료 간 관계 수정 응답")
public class UpdateEdgeResDto {
  @Schema(description = "관계 ID", example = "1")
  private Long edgeId;

  @Schema(description = "첫 번째 재료 ID", example = "1")
  private Long fromIngredientId;

  @Schema(description = "두 번째 재료 ID", example = "2")
  private Long toIngredientId;

  @Schema(description = "관계 타입", example = "PAIR_WELL")
  private IngredientEdgeEntity.IngredientRelationType relationType;

  @Schema(description = "관계 점수", example = "0.700")
  private BigDecimal score;

  @Schema(description = "신뢰도", example = "0.850")
  private BigDecimal confidence;

  @Schema(description = "관계 요약", example = "토마토와 바질은 클래식한 조합입니다.")
  private String reasonSummary;

  @Schema(description = "수정 일시", example = "2026-01-16T10:30:00Z")
  private Instant updatedAt;
}
