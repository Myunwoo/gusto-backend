package com.gustoexpedition.recipe.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.gustoexpedition.recipe.adapter.in.dto
 * fileName       : UpdateAliasReqDto
 * author         : fddsg
 * date           : 2026-01-20
 * description    : 레시피(recipe) 별칭 개별 수정 요청 DTO
 */
@Getter
@Setter
@Schema(description = "레시피 별칭 개별 수정 요청")
public class UpdateAliasReqDto {

    @NotNull(message = "ALIAS002")
    @Min(value = 1, message = "ALIAS002")
    @Schema(description = "레시피 별칭 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long aliasId;

    @NotBlank(message = "ALIAS003")
    @Size(max = 100, message = "ALIAS004")
    @Schema(description = "수정할 레시피 별칭", example = "토마토 파스타", requiredMode = Schema.RequiredMode.REQUIRED)
    private String alias;
}

