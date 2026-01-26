package com.gustoexpedition.recipe.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * packageName    : com.gustoexpedition.recipe.adapter.in.dto
 * fileName       : RecipeAliasDto
 * author         : fddsg
 * date           : 2026-01-20
 * description    : 레시피(recipe) 별칭 정보 DTO (ID 포함)
 */
@Getter
@AllArgsConstructor
@Schema(description = "레시피 별칭 정보")
public class RecipeAliasDto {
    @Schema(description = "레시피 별칭 ID", example = "1")
    private Long aliasId;

    @Schema(description = "레시피 별칭", example = "토마토 파스타")
    private String alias;
}

