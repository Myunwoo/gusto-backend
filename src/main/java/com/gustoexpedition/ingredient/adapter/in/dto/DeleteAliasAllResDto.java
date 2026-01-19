package com.gustoexpedition.ingredient.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * packageName    : com.gustoexpedition.ingredient.adapter.in.dto
 * fileName       : DeleteAliasAllResDto
 * author         : fddsg
 * date           : 2026-01-16
 * description    : 재료(ingredient) 별칭 일괄 삭제 응답 DTO
 */
@Getter
@AllArgsConstructor
@Schema(description = "재료 별칭 일괄 삭제 응답")
public class DeleteAliasAllResDto {
    @Schema(description = "재료 ID", example = "1")
    private Long ingredientId;

    @Schema(description = "언어 코드", example = "ko-KR")
    private String locale;

    @Schema(description = "삭제 성공 메시지", example = "재료의 별칭이 성공적으로 삭제되었습니다.")
    private String message;
}
