package com.gustoexpedition.ingredient.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName : com.gustoexpedition.ingredient.adapter.in.dto
 * fileName : AddCollectionItemReqDto
 * author : fddsg
 * date : 2026-01-16
 * description : 컬렉션에 재료 추가 요청 DTO
 */
@Getter
@Setter
@Schema(description = "컬렉션에 재료 추가 요청")
public class AddCollectionItemReqDto {

  @NotNull(message = "COLLECTION003")
  @Min(value = 1, message = "COLLECTION003")
  @Schema(description = "컬렉션 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
  private Long collectionId;

  @NotNull(message = "INGR005")
  @Min(value = 1, message = "INGR005")
  @Schema(description = "재료 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
  private Long ingredientId;

  @Schema(description = "추가 순서 (기본값: 마지막 순서)", example = "1")
  private Integer addedOrder;
}
