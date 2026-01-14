package com.gustoexpedition.ingredient.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * packageName    : com.gustoexpedition.ingredient.adapter.in.dto
 * fileName       : CreateIngredientReqDto
 * author         : fddsg
 * date           : 2026-01-14
 * description    : 재료 생성 요청 DTO
 */
@Getter
@Setter
@Schema(description = "재료 생성 요청")
public class CreateIngredientReqDto {

    @NotBlank(message = "재료 이름은 필수입니다")
    @Size(max = 100, message = "재료 이름은 100자 이하여야 합니다")
    @Schema(description = "재료 이름", example = "토마토", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Size(max = 10, message = "locale은 10자 이하여야 합니다")
    @Schema(description = "언어 코드", example = "ko-KR", defaultValue = "ko-KR")
    private String locale = "ko-KR"; // 기본값

    @Schema(description = "재료 설명", example = "빨간색 과일로 다양한 요리에 사용됩니다.")
    private String description;

    @Schema(description = "썸네일 이미지 URL", example = "https://example.com/images/tomato.jpg")
    private String thumbnailUrl;

    @Schema(description = "활성화 여부", example = "true", defaultValue = "true")
    private Boolean isActive = true;

    @Schema(description = "재료 별칭 목록", example = "[\"토마토\", \"방울토마토\"]")
    private List<String> aliases; // 별칭 목록
}
