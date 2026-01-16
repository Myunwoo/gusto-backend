package com.gustoexpedition.ingredient.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.gustoexpedition.ingredient.adapter.in.dto
 * fileName       : UpdateIngredientBasicReqDto
 * author         : fddsg
 * date           : 2026-01-16
 * description    : 재료 기본정보 수정 요청 DTO
 */
@Getter
@Setter
@Schema(description = "재료 기본정보 수정 요청")
public class UpdateIngredientBasicReqDto {

    @NotNull(message = "INGR005")
    @Min(value = 1, message = "INGR005")
    @Schema(description = "재료 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long ingredientId;

    @NotBlank(message = "INGR001")
    @Size(max = 100, message = "INGR004")
    @Schema(description = "재료 국문명", example = "토마토", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "썸네일 이미지 URL", example = "https://example.com/images/tomato.jpg")
    private String thumbnailUrl;

    @Schema(description = "활성화 여부", example = "true", defaultValue = "true")
    private Boolean isActive = true;
}
