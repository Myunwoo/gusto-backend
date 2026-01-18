package com.gustoexpedition.recipe.adapter.in.dto;

import com.gustoexpedition.common.annotation.XssSafe;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName : com.gustoexpedition.recipe.adapter.in.dto
 * fileName : UpdateRecipeReqDto
 * author : fddsg
 * date : 2026-01-16
 * description : 레시피 수정 요청 DTO
 */
@Getter
@Setter
@Schema(description = "레시피 수정 요청")
public class UpdateRecipeReqDto {

    @NotNull(message = "RECIPE003")
    @Min(value = 1, message = "RECIPE003")
    @Schema(description = "레시피 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long recipeId;

    @XssSafe
    @NotBlank(message = "RECIPE001")
    @Size(max = 120, message = "RECIPE002")
    @Schema(description = "레시피 제목", example = "토마토 파스타", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @Schema(description = "레시피 설명", example = "간단하고 맛있는 토마토 파스타입니다.")
    private String description;

    @Schema(description = "조리 방법", example = "1. 파스타를 끓는 물에 넣고 10분간 삶는다.")
    private String instructions;

    @Schema(description = "인분", example = "2")
    private Integer servings;

    @Schema(description = "조리 시간 (분)", example = "20")
    private Integer cookTimeMinutes;
}

