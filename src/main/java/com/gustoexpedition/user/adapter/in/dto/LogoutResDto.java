package com.gustoexpedition.user.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * packageName : com.gustoexpedition.user.adapter.in.dto
 * fileName : LogoutResDto
 * author : fddsg
 * date : 2026-01-16
 * description : 로그아웃 응답 DTO
 */
@Getter
@AllArgsConstructor
@Schema(description = "로그아웃 응답")
public class LogoutResDto {
    @Schema(description = "로그아웃 성공 메시지", example = "로그아웃되었습니다.")
    private String message;
}

