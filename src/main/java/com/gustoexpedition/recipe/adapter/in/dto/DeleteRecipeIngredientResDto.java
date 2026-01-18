package com.gustoexpedition.recipe.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * packageName : com.gustoexpedition.recipe.adapter.in.dto
 * fileName : DeleteRecipeIngredientResDto
 * author : fddsg
 * date : 2026-01-16
 * description : 레시피 재료 삭제 응답 DTO
 */
@Getter
@AllArgsConstructor
@Schema(description = "레시피 재료 삭제 응답")
public class DeleteRecipeIngredientResDto {
    @Schema(description = "삭제된 레시피 재료 ID", example = "1")
    private Long recipeIngredientId;

    @Schema(description = "삭제 성공 메시지", example = "레시피 재료가 성공적으로 삭제되었습니다.")
    private String message;
}

