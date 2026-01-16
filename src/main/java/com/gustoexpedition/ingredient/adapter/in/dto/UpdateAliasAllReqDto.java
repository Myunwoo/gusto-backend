package com.gustoexpedition.ingredient.adapter.in.dto;

import com.gustoexpedition.common.annotation.ValidLocale;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * packageName    : com.gustoexpedition.ingredient.adapter.in.dto
 * fileName       : UpdateAliasAllReqDto
 * author         : fddsg
 * date           : 2026-01-16
 * description    : 재료 별칭 일괄 수정 요청 DTO (기존 별칭 삭제 후 새로 추가)
 */
@Getter
@Setter
@Schema(description = "재료 별칭 일괄 수정 요청")
public class UpdateAliasAllReqDto {

    @NotNull(message = "INGR005")
    @Min(value = 1, message = "INGR005")
    @Schema(description = "재료 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long ingredientId;

    @ValidLocale
    @NotNull(message = "LOCALE001")
    @Schema(description = "언어 코드. 지원 locale: ko-KR, ja-JP, fr-FR, it-IT, en-US", example = "ko-KR", requiredMode = Schema.RequiredMode.REQUIRED)
    private String locale;

    @NotEmpty(message = "ALAIS001")
    @Schema(description = "별칭 목록 (기존 별칭은 삭제되고 이 목록으로 교체됩니다)", example = "[\"방울토마토\", \"체리토마토\"]", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> aliases;
}
