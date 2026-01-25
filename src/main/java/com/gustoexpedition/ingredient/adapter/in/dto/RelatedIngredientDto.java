package com.gustoexpedition.ingredient.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * packageName : com.gustoexpedition.ingredient.adapter.in.dto
 * fileName : RelatedIngredientDto
 * author : fddsg
 * date : 2026-01-15
 * description : 관련 재료 정보 DTO
 */
@Getter
@AllArgsConstructor
@Schema(description = "관련 재료 정보")
public class RelatedIngredientDto {

    @Schema(description = "관련 재료 ID", example = "2")
    private Long ingredientId;

    @Schema(description = "관련 재료 이름", example = "바질")
    private String name;

    @Schema(description = "관계 타입", example = "PAIR_WELL", allowableValues = { "PAIR_WELL", "AVOID", "NEUTRAL" })
    private String relationType;

    @Schema(description = "관계 점수 (1-10)", example = "5")
    private Integer score;

    @Schema(description = "신뢰도", example = "0.950")
    private BigDecimal confidence;

    @Schema(description = "관계 이유 요약", example = "클래식한 조합으로 잘 어울립니다")
    private String reasonSummary;
}
