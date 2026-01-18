package com.gustoexpedition.user.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * packageName : com.gustoexpedition.user.adapter.in.dto
 * fileName : RefreshTokenResDto
 * author : fddsg
 * date : 2026-01-16
 * description : Refresh Token 갱신 응답 DTO
 */
@Getter
@AllArgsConstructor
@Schema(description = "Refresh Token 갱신 응답")
public class RefreshTokenResDto {
  @Schema(description = "새로운 Access Token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
  private String accessToken;
}
