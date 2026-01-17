package com.gustoexpedition.ingredient.adapter.in.dto;

import com.gustoexpedition.ingredient.entity.IngredientEdgeEvidenceEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName : com.gustoexpedition.ingredient.adapter.in.dto
 * fileName : CreateEvidenceReqDto
 * author : fddsg
 * date : 2026-01-16
 * description : 재료 간 관계 증거 생성 요청 DTO
 */
@Getter
@Setter
@Schema(description = "재료 간 관계 증거 생성 요청")
public class CreateEvidenceReqDto {

  @NotNull(message = "EVIDENCE001")
  @Min(value = 1, message = "EVIDENCE001")
  @Schema(description = "관계 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
  private Long edgeId;

  @NotNull(message = "EVIDENCE002")
  @Schema(description = "증거 타입 (NOTE, BOOK, VIDEO, EXPERIMENT, RECIPE_REFERENCE, LINK)", example = "BOOK", requiredMode = Schema.RequiredMode.REQUIRED)
  private IngredientEdgeEvidenceEntity.EdgeEvidenceType evidenceType;

  @Size(max = 200, message = "EVIDENCE003")
  @Schema(description = "제목", example = "요리 백과사전")
  private String title;

  @Schema(description = "내용", example = "토마토와 바질의 조합에 대한 설명...")
  private String content;

  @Schema(description = "출처 참조", example = "https://example.com/reference")
  private String sourceRef;
}
