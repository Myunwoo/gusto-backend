package com.gustoexpedition.ingredient.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * packageName    : com.gustoexpedition.ingredient.adapter.in.dto
 * fileName       : AliasDto
 * author         : fddsg
 * date           : 2026-01-16
 * description    : 별칭 정보 DTO (ID 포함)
 */
@Getter
@AllArgsConstructor
@Schema(description = "별칭 정보")
public class AliasDto {
    @Schema(description = "별칭 ID", example = "1")
    private Long aliasId;

    @Schema(description = "별칭", example = "방울토마토")
    private String alias;
}
