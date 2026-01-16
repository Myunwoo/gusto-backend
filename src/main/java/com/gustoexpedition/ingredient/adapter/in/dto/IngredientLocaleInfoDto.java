package com.gustoexpedition.ingredient.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * packageName    : com.gustoexpedition.ingredient.adapter.in.dto
 * fileName       : IngredientLocaleInfoDto
 * author         : fddsg
 * date           : 2026-01-16
 * description    : 재료의 locale별 정보 DTO
 */
@Getter
@AllArgsConstructor
@Schema(description = "재료의 locale별 정보")
public class IngredientLocaleInfoDto {
    
    @Schema(description = "재료 이름", example = "토마토")
    private String name;
    
    @Schema(description = "재료 설명", example = "빨간색 과일로 다양한 요리에 사용됩니다.")
    private String description;
}
