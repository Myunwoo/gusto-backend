package com.gustoexpedition.recipe.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

/**
 * packageName    : com.gustoexpedition.recipe.adapter.in.dto
 * fileName       : UpdateAliasAllResDto
 * author         : fddsg
 * date           : 2026-01-20
 * description    : 레시피(recipe) 별칭 일괄 수정 응답 DTO
 */
@Getter
@AllArgsConstructor
@Schema(description = "레시피 별칭 일괄 수정 응답")
public class UpdateAliasAllResDto {
    @Schema(description = "레시피 ID", example = "1")
    private Long recipeId;

    @Schema(description = "언어 코드", example = "ko-KR")
    private String locale;

    @Schema(description = "수정된 레시피 별칭 목록", example = "[\"토마토 파스타\", \"파스타\"]")
    private List<String> aliases;

    @Schema(description = "생성 일시 (레시피 별칭은 삭제 후 새로 생성되므로 생성 일시 반환)", example = "2026-01-20T10:30:00Z")
    private Instant createdAt;
}

