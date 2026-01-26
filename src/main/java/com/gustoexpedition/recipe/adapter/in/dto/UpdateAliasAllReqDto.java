package com.gustoexpedition.recipe.adapter.in.dto;

import com.gustoexpedition.common.annotation.ValidLocale;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * packageName : com.gustoexpedition.recipe.adapter.in.dto
 * fileName : UpdateAliasAllReqDto
 * author : fddsg
 * date : 2026-01-20
 * description : 레시피(recipe) 별칭 일괄 수정 요청 DTO (기존 레시피 별칭 삭제 후 새로 추가)
 */
@Getter
@Setter
@Schema(description = "레시피 별칭 일괄 수정 요청")
public class UpdateAliasAllReqDto {

    @NotNull(message = "RECIPE001")
    @Min(value = 1, message = "RECIPE001")
    @Schema(description = "레시피 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long recipeId;

    @ValidLocale
    @NotNull(message = "LOCALE001")
    @Schema(description = "언어 코드. 지원 locale: ko-KR, ja-JP, fr-FR, it-IT, en-US", example = "ko-KR", requiredMode = Schema.RequiredMode.REQUIRED)
    private String locale;

    @NotNull(message = "ALIAS001")
    @Schema(description = "레시피 별칭 목록 (기존 레시피 별칭은 삭제되고 이 목록으로 교체됩니다. 빈 배열도 허용됩니다)", example = "[\"토마토 파스타\", \"파스타\"]", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> aliases;
}

