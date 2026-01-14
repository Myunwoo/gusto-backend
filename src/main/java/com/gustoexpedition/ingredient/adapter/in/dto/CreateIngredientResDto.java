package com.gustoexpedition.ingredient.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

/**
 * packageName    : com.gustoexpedition.ingredient.adapter.in.dto
 * fileName       : CreateIngredientResDto
 * author         : fddsg
 * date           : 2026-01-14
 * description    : 재료 생성 응답 DTO
 */
@Getter
@AllArgsConstructor
@Schema(description = "재료 생성 응답")
public class CreateIngredientResDto {
    @Schema(description = "재료 ID", example = "1")
    private Long ingredientId;
    
    @Schema(description = "재료 이름", example = "토마토")
    private String name;
    
    @Schema(description = "언어 코드", example = "ko-KR")
    private String locale;
    
    @Schema(description = "재료 설명", example = "빨간색 과일로 다양한 요리에 사용됩니다.")
    private String description;
    
    @Schema(description = "썸네일 이미지 URL", example = "https://example.com/images/tomato.jpg")
    private String thumbnailUrl;
    
    @Schema(description = "활성화 여부", example = "true")
    private Boolean isActive;
    
    @Schema(description = "생성 일시", example = "2026-01-14T10:30:00Z")
    private Instant createdAt;
}
