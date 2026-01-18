package com.gustoexpedition.user.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * packageName : com.gustoexpedition.user.adapter.in.dto
 * fileName : LoginResDto
 * author : fddsg
 * date : 2026-01-16
 * description : 로그인 응답 DTO
 */
@Getter
@AllArgsConstructor
@Schema(description = "로그인 응답")
public class LoginResDto {
    @Schema(description = "Access Token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String accessToken;

    @Schema(description = "Refresh Token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String refreshToken;

    @Schema(description = "사용자 번호", example = "12345678")
    private String userNum;
}

