package com.gustoexpedition.ingredient.adapter.in.dto;

import com.gustoexpedition.common.annotation.XssSafe;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName : com.gustoexpedition.ingredient.adapter.in.dto
 * fileName : CreateCollectionReqDto
 * author : fddsg
 * date : 2026-01-16
 * description : 재료 컬렉션 생성 요청 DTO
 */
@Getter
@Setter
@Schema(description = "재료 컬렉션 생성 요청")
public class CreateCollectionReqDto {

  @XssSafe
  @NotBlank(message = "COLLECTION001")
  @Size(max = 120, message = "COLLECTION002")
  @Schema(description = "컬렉션 제목", example = "내가 좋아하는 재료", requiredMode = Schema.RequiredMode.REQUIRED)
  private String title;

  @Schema(description = "컬렉션 설명", example = "요리에 자주 사용하는 재료들을 모았습니다.")
  private String note;
}
