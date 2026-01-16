package com.gustoexpedition.ingredient.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

/**
 * packageName    : com.gustoexpedition.ingredient.adapter.in.dto
 * fileName       : UpdateAliasResDto
 * author         : fddsg
 * date           : 2026-01-16
 * description    : 재료 별칭 개별 수정 응답 DTO
 */
@Getter
@AllArgsConstructor
@Schema(description = "재료 별칭 개별 수정 응답")
public class UpdateAliasResDto {
    @Schema(description = "별칭 ID", example = "1")
    private Long aliasId;

    @Schema(description = "재료 ID", example = "1")
    private Long ingredientId;

    @Schema(description = "언어 코드", example = "ko-KR")
    private String locale;

    @Schema(description = "수정된 별칭", example = "방울토마토")
    private String alias;

    @Schema(description = "생성 일시", example = "2026-01-16T10:30:00Z")
    private Instant createdAt;
}
