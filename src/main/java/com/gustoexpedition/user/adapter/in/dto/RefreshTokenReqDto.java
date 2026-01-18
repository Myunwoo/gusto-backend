package com.gustoexpedition.user.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName : com.gustoexpedition.user.adapter.in.dto
 * fileName : RefreshTokenReqDto
 * author : fddsg
 * date : 2026-01-16
 * description : Refresh Token 갱신 요청 DTO
 */
@Getter
@Setter
@Schema(description = "Refresh Token 갱신 요청")
public class RefreshTokenReqDto {

    @NotBlank(message = "USER011")
    @Schema(description = "Refresh Token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...", requiredMode = Schema.RequiredMode.REQUIRED)
    private String refreshToken;
}

