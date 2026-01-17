package com.gustoexpedition.ingredient.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * packageName : com.gustoexpedition.ingredient.adapter.in.dto
 * fileName : DeleteEdgeResDto
 * author : fddsg
 * date : 2026-01-16
 * description : 재료 간 관계 삭제 응답 DTO
 */
@Getter
@AllArgsConstructor
@Schema(description = "재료 간 관계 삭제 응답")
public class DeleteEdgeResDto {
  @Schema(description = "삭제된 관계 ID", example = "1")
  private Long edgeId;

  @Schema(description = "삭제 성공 메시지", example = "재료 간 관계가 성공적으로 삭제되었습니다.")
  private String message;
}
