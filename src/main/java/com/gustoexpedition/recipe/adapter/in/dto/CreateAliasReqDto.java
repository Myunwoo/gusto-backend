package com.gustoexpedition.recipe.adapter.in.dto;

import com.gustoexpedition.common.annotation.ValidLocale;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * packageName    : com.gustoexpedition.recipe.adapter.in.dto
 * fileName       : CreateAliasReqDto
 * author         : fddsg
 * date           : 2026-01-20
 * description    : 레시피(recipe) 별칭 생성 요청 DTO
 */
@Getter
@Setter
@Schema(description = "레시피 별칭 생성 요청")
public class CreateAliasReqDto {

    @NotNull(message = "RECIPE001")
    @Schema(description = "레시피 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long recipeId;

    @ValidLocale
    @Schema(description = "언어 코드. 지원 locale: ko-KR, ja-JP, fr-FR, it-IT, en-US", example = "ko-KR", requiredMode = Schema.RequiredMode.REQUIRED)
    private String locale;

    @NotEmpty(message = "ALIAS001")
    @Schema(description = "레시피 별칭 목록", example = "[\"토마토 파스타\", \"파스타\"]", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> aliases;
}

