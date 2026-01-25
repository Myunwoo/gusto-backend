package com.gustoexpedition.ingredient.adapter.in.dto;

import com.gustoexpedition.ingredient.entity.IngredientEdgeEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * packageName : com.gustoexpedition.ingredient.adapter.in.dto
 * fileName : UpdateEdgeReqDto
 * author : fddsg
 * date : 2026-01-16
 * description : 재료 간 관계 수정 요청 DTO
 */
@Getter
@Setter
@Schema(description = "재료 간 관계 수정 요청")
public class UpdateEdgeReqDto {

  @NotNull(message = "EDGE008")
  @Min(value = 1, message = "EDGE008")
  @Schema(description = "관계 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
  private Long edgeId;

  @NotNull(message = "EDGE003")
  @Schema(description = "관계 타입 (PAIR_WELL: 궁합, AVOID: 비궁합, NEUTRAL: 중립)", example = "PAIR_WELL", requiredMode = Schema.RequiredMode.REQUIRED)
  private IngredientEdgeEntity.IngredientRelationType relationType;

  @Schema(description = "관계 점수 (PAIR_WELL/AVOID: 1-10 필수, NEUTRAL: 선택사항)", example = "5", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  private Integer score;

  @DecimalMin(value = "0.000", message = "EDGE006")
  @DecimalMax(value = "1.000", message = "EDGE006")
  @Schema(description = "신뢰도 (0.000 ~ 1.000)", example = "0.850")
  private BigDecimal confidence;

  @Size(max = 255, message = "EDGE007")
  @Schema(description = "관계 요약", example = "토마토와 바질은 클래식한 조합입니다.")
  private String reasonSummary;
}
