package com.gustoexpedition.user.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * packageName : com.gustoexpedition.user.adapter.in.dto
 * fileName : UpdateUserResDto
 * author : fddsg
 * date : 2026-01-16
 * description : 회원정보 수정 응답 DTO
 */
@Getter
@AllArgsConstructor
@Schema(description = "회원정보 수정 응답")
public class UpdateUserResDto {
  @Schema(description = "사용자 번호 (8자리)", example = "12345678")
  private String userNum;
}
