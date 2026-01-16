package com.gustoexpedition.ingredient.adapter.in.dto;

import com.gustoexpedition.common.annotation.ValidLocale;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.gustoexpedition.ingredient.adapter.in.dto
 * fileName       : UpdateIngredientI18nReqDto
 * author         : fddsg
 * date           : 2026-01-16
 * description    : 재료 locale별 기본정보 수정 요청 DTO
 */
@Getter
@Setter
@Schema(description = "재료 locale별 기본정보 수정 요청")
public class UpdateIngredientI18nReqDto {

    @NotNull(message = "INGR005")
    @Min(value = 1, message = "INGR005")
    @Schema(description = "재료 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long ingredientId;

    @ValidLocale
    @NotBlank(message = "LOCALE001")
    @Schema(description = "언어 코드. 지원 locale: ko-KR, ja-JP, fr-FR, it-IT, en-US", example = "ko-KR", requiredMode = Schema.RequiredMode.REQUIRED)
    private String locale;

    @NotBlank(message = "INGR001")
    @Size(max = 100, message = "INGR004")
    @Schema(description = "재료 이름 (해당 locale)", example = "토마토", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "재료 설명 (해당 locale)", example = "빨간색 과일로 다양한 요리에 사용됩니다.")
    private String description;
}
