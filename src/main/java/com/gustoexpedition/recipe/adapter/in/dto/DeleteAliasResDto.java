package com.gustoexpedition.recipe.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * packageName    : com.gustoexpedition.recipe.adapter.in.dto
 * fileName       : DeleteAliasResDto
 * author         : fddsg
 * date           : 2026-01-20
 * description    : 레시피(recipe) 별칭 개별 삭제 응답 DTO
 */
@Getter
@AllArgsConstructor
@Schema(description = "레시피 별칭 개별 삭제 응답")
public class DeleteAliasResDto {
    @Schema(description = "삭제된 레시피 별칭 ID", example = "1")
    private Long aliasId;

    @Schema(description = "레시피 ID", example = "1")
    private Long recipeId;

    @Schema(description = "언어 코드", example = "ko-KR")
    private String locale;

    @Schema(description = "삭제된 레시피 별칭", example = "토마토 파스타")
    private String alias;

    @Schema(description = "삭제 성공 메시지", example = "별칭이 성공적으로 삭제되었습니다.")
    private String message;
}

