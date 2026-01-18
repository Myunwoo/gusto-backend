package com.gustoexpedition.user.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * packageName : com.gustoexpedition.user.adapter.in.dto
 * fileName : SelectUserResDto
 * author : fddsg
 * date : 2026-01-16
 * description : 회원정보 조회 응답 DTO
 */
@Getter
@AllArgsConstructor
@Schema(description = "회원정보 조회 응답")
public class SelectUserResDto {
    @Schema(description = "사용자 번호 (8자리)", example = "12345678")
    private String userNum;

    @Schema(description = "이메일", example = "user@example.com")
    private String email;

    @Schema(description = "닉네임", example = "홍길동")
    private String nickname;

    @Schema(description = "역할", example = "USER")
    private String role;

    @Schema(description = "활성화 여부", example = "true")
    private Boolean isActive;

    @Schema(description = "생성일시")
    private String createdAt;
}

