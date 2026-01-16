package com.gustoexpedition.ingredient.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * packageName    : com.gustoexpedition.ingredient.adapter.in.dto
 * fileName       : SelectIngredientResDto
 * author         : fddsg
 * date           : 2026-01-16
 * description    : 재료 조회 응답 DTO
 */
@Getter
@Setter
@AllArgsConstructor
@Schema(description = "재료 조회 응답")
public class SelectIngredientResDto {
    @Schema(description = "재료 ID", example = "1")
    private Long ingredientId;
    
    @Schema(description = "locale별 재료 정보 (locale이 없으면 모든 locale, 있으면 해당 locale만)", 
            example = "{\"ko-KR\": {\"name\": \"토마토\", \"description\": \"빨간색 과일\"}}")
    private Map<String, IngredientLocaleInfoDto> localeInfo;
    
    @Schema(description = "썸네일 이미지 URL", example = "https://example.com/tomato.jpg")
    private String thumbnailUrl;
    
    @Schema(description = "활성화 여부", example = "true")
    private Boolean isActive;
    
    @Schema(description = "locale별 별칭 목록 (locale명: [별칭 배열])", 
            example = "{\"ko-KR\": [\"방울토마토\", \"체리토마토\"]}")
    private Map<String, List<String>> aliases;
    
    @Schema(description = "관련 재료 목록 (includeRelationYn=true일 때만 포함)")
    private List<RelatedIngredientDto> relatedIngredients;
    
    @Schema(description = "생성 시간", example = "2023-01-01T12:00:00Z")
    private Instant createdAt;
    
    @Schema(description = "수정 시간", example = "2023-01-02T12:00:00Z")
    private Instant updatedAt;
}
