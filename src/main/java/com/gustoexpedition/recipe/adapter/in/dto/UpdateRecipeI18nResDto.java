package com.gustoexpedition.recipe.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

/**
 * packageName : com.gustoexpedition.recipe.adapter.in.dto
 * fileName : UpdateRecipeI18nResDto
 * author : fddsg
 * date : 2026-01-20
 * description : 레시피 Locale별 정보 수정 응답 DTO
 */
@Getter
@AllArgsConstructor
@Schema(description = "레시피 Locale별 정보 수정 응답")
public class UpdateRecipeI18nResDto {
    @Schema(description = "레시피 ID", example = "1")
    private Long recipeId;

    @Schema(description = "언어 코드", example = "ko-KR")
    private String locale;

    @Schema(description = "레시피 설명", example = "간단하고 맛있는 토마토 파스타입니다.")
    private String description;

    @Schema(description = "조리 방법", example = "1. 파스타를 끓는 물에 넣고 10분간 삶는다.")
    private String instructions;

    @Schema(description = "생성 일시", example = "2026-01-16T10:30:00Z")
    private Instant createdAt;

    @Schema(description = "수정 일시", example = "2026-01-16T10:30:00Z")
    private Instant updatedAt;
}

