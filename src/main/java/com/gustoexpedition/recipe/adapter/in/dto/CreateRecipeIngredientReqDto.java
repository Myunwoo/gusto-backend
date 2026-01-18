package com.gustoexpedition.recipe.adapter.in.dto;

import com.gustoexpedition.common.annotation.XssSafe;
import com.gustoexpedition.recipe.entity.RecipeIngredientRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * packageName : com.gustoexpedition.recipe.adapter.in.dto
 * fileName : CreateRecipeIngredientReqDto
 * author : fddsg
 * date : 2026-01-16
 * description : 레시피 재료 추가 요청 DTO
 */
@Getter
@Setter
@Schema(description = "레시피 재료 추가 요청")
public class CreateRecipeIngredientReqDto {

    @NotNull(message = "RECIPE003")
    @Min(value = 1, message = "RECIPE003")
    @Schema(description = "레시피 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long recipeId;

    @NotNull(message = "INGR005")
    @Min(value = 1, message = "INGR005")
    @Schema(description = "재료 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long ingredientId;

    @Schema(description = "역할 (REQUIRED, OPTIONAL)", example = "REQUIRED", defaultValue = "REQUIRED")
    private RecipeIngredientRole role = RecipeIngredientRole.REQUIRED;

    @Schema(description = "양", example = "120.000")
    private BigDecimal amount;

    @XssSafe
    @Size(max = 30, message = "RECIPE005")
    @Schema(description = "단위", example = "g")
    private String unit;

    @XssSafe
    @Size(max = 255, message = "RECIPE006")
    @Schema(description = "비고", example = "얇게 썬 것")
    private String note;
}

