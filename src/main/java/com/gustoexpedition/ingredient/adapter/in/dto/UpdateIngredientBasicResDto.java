package com.gustoexpedition.ingredient.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

/**
 * packageName    : com.gustoexpedition.ingredient.adapter.in.dto
 * fileName       : UpdateIngredientBasicResDto
 * author         : fddsg
 * date           : 2026-01-16
 * description    : 재료 기본정보 수정 응답 DTO
 */
@Getter
@AllArgsConstructor
@Schema(description = "재료 기본정보 수정 응답")
public class UpdateIngredientBasicResDto {
    @Schema(description = "재료 ID", example = "1")
    private Long ingredientId;

    @Schema(description = "재료 국문명", example = "토마토")
    private String name;

    @Schema(description = "썸네일 이미지 URL", example = "https://example.com/images/tomato.jpg")
    private String thumbnailUrl;

    @Schema(description = "활성화 여부", example = "true")
    private Boolean isActive;

    @Schema(description = "수정 일시", example = "2026-01-16T10:30:00Z")
    private Instant updatedAt;
}
