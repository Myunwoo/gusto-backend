package com.gustoexpedition.ingredient.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.gustoexpedition.ingredient.adapter.in.dto
 * fileName       : UpdateAliasReqDto
 * author         : fddsg
 * date           : 2026-01-16
 * description    : 재료 별칭 개별 수정 요청 DTO
 */
@Getter
@Setter
@Schema(description = "재료 별칭 개별 수정 요청")
public class UpdateAliasReqDto {

    @NotNull(message = "INGR005")
    @Min(value = 1, message = "INGR005")
    @Schema(description = "별칭 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long aliasId;

    @NotBlank(message = "INGR010")
    @Size(max = 100, message = "INGR011")
    @Schema(description = "수정할 별칭", example = "방울토마토", requiredMode = Schema.RequiredMode.REQUIRED)
    private String alias;
}
