package com.gustoexpedition.ingredient.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

/**
 * packageName    : com.gustoexpedition.ingredient.adapter.in.dto
 * fileName       : UpdateAliasAllResDto
 * author         : fddsg
 * date           : 2026-01-16
 * description    : 재료(ingredient) 별칭 일괄 수정 응답 DTO
 */
@Getter
@AllArgsConstructor
@Schema(description = "재료 별칭 일괄 수정 응답")
public class UpdateAliasAllResDto {
    @Schema(description = "재료 ID", example = "1")
    private Long ingredientId;

    @Schema(description = "언어 코드", example = "ko-KR")
    private String locale;

    @Schema(description = "수정된 재료 별칭 목록", example = "[\"방울토마토\", \"체리토마토\"]")
    private List<String> aliases;

    @Schema(description = "생성 일시 (재료 별칭은 삭제 후 새로 생성되므로 생성 일시 반환)", example = "2026-01-16T10:30:00Z")
    private Instant createdAt;
}
