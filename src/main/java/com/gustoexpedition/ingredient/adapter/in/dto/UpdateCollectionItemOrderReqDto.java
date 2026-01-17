package com.gustoexpedition.ingredient.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName : com.gustoexpedition.ingredient.adapter.in.dto
 * fileName : UpdateCollectionItemOrderReqDto
 * author : fddsg
 * date : 2026-01-16
 * description : 컬렉션 아이템 순서 수정 요청 DTO
 */
@Getter
@Setter
@Schema(description = "컬렉션 아이템 순서 수정 요청")
public class UpdateCollectionItemOrderReqDto {

  @NotNull(message = "COLLECTION004")
  @Min(value = 1, message = "COLLECTION004")
  @Schema(description = "아이템 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
  private Long collectionItemId;

  @Schema(description = "새로운 순서", example = "2")
  private Integer addedOrder;
}
