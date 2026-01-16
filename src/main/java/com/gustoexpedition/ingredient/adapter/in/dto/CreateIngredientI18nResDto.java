package com.gustoexpedition.ingredient.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

/**
 * packageName    : com.gustoexpedition.ingredient.adapter.in.dto
 * fileName       : CreateIngredientI18nResDto
 * author         : fddsg
 * date           : 2026-01-16
 * description    : 재료 locale별 기본정보 생성 응답 DTO
 */
@Getter
@AllArgsConstructor
@Schema(description = "재료 locale별 기본정보 생성 응답")
public class CreateIngredientI18nResDto {
    @Schema(description = "재료 ID", example = "1")
    private Long ingredientId;

    @Schema(description = "언어 코드", example = "ko-KR")
    private String locale;

    @Schema(description = "재료 이름", example = "토마토")
    private String name;

    @Schema(description = "재료 설명", example = "빨간색 과일로 다양한 요리에 사용됩니다.")
    private String description;

    @Schema(description = "생성 일시", example = "2026-01-16T10:30:00Z")
    private Instant createdAt;

    @Schema(description = "수정 일시", example = "2026-01-16T10:30:00Z")
    private Instant updatedAt;
}
