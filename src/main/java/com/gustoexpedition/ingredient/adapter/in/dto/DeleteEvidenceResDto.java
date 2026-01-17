package com.gustoexpedition.ingredient.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * packageName : com.gustoexpedition.ingredient.adapter.in.dto
 * fileName : DeleteEvidenceResDto
 * author : fddsg
 * date : 2026-01-16
 * description : 재료 간 관계 증거 삭제 응답 DTO
 */
@Getter
@AllArgsConstructor
@Schema(description = "재료 간 관계 증거 삭제 응답")
public class DeleteEvidenceResDto {
  @Schema(description = "삭제된 증거 ID", example = "1")
  private Long evidenceId;

  @Schema(description = "삭제 성공 메시지", example = "증거가 성공적으로 삭제되었습니다.")
  private String message;
}
