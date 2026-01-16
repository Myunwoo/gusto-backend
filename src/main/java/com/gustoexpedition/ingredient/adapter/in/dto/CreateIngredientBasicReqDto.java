package com.gustoexpedition.ingredient.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.gustoexpedition.ingredient.adapter.in.dto
 * fileName       : CreateIngredientBasicReqDto
 * author         : fddsg
 * date           : 2026-01-16
 * description    : 재료 기본정보 생성 요청 DTO
 */
@Getter
@Setter
@Schema(description = "재료 기본정보 생성 요청")
public class CreateIngredientBasicReqDto {

    @NotBlank(message = "재료 이름은 필수입니다")
    @Size(max = 100, message = "재료 이름은 100자 이하여야 합니다")
    @Schema(description = "재료 국문명", example = "토마토", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "썸네일 이미지 URL", example = "https://example.com/images/tomato.jpg")
    private String thumbnailUrl;

    @Schema(description = "활성화 여부", example = "true", defaultValue = "true")
    private Boolean isActive = true;
}
