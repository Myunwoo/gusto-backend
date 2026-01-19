package com.gustoexpedition.ingredient.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * packageName    : com.gustoexpedition.ingredient.adapter.in.dto
 * fileName       : IngredientAliasDto
 * author         : fddsg
 * date           : 2026-01-16
 * description    : 재료(ingredient) 별칭 정보 DTO (ID 포함)
 */
@Getter
@AllArgsConstructor
@Schema(description = "재료 별칭 정보")
public class IngredientAliasDto {
    @Schema(description = "재료 별칭 ID", example = "1")
    private Long aliasId;

    @Schema(description = "재료 별칭", example = "방울토마토")
    private String alias;
}
