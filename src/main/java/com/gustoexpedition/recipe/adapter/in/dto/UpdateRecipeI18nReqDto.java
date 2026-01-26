package com.gustoexpedition.recipe.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName : com.gustoexpedition.recipe.adapter.in.dto
 * fileName : UpdateRecipeI18nReqDto
 * author : fddsg
 * date : 2026-01-20
 * description : 레시피 Locale별 정보 수정 요청 DTO (upsert: 없으면 생성, 있으면 수정)
 */
@Getter
@Setter
@Schema(description = "레시피 Locale별 정보 수정 요청")
public class UpdateRecipeI18nReqDto {

    @NotNull(message = "RECIPE003")
    @Min(value = 1, message = "RECIPE003")
    @Schema(description = "레시피 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long recipeId;

    @NotBlank(message = "RECIPE010")
    @Size(max = 10, message = "RECIPE011")
    @Schema(description = "언어 코드", example = "ko-KR", requiredMode = Schema.RequiredMode.REQUIRED)
    private String locale;

    @Schema(description = "레시피 설명", example = "간단하고 맛있는 토마토 파스타입니다.")
    private String description;

    @Schema(description = "조리 방법", example = "1. 파스타를 끓는 물에 넣고 10분간 삶는다.\n2. 토마토를 볶는다.\n3. 파스타와 토마토를 섞는다.")
    private String instructions;
}

