package com.gustoexpedition.user.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName : com.gustoexpedition.user.adapter.in.dto
 * fileName : CreateUserReqDto
 * author : fddsg
 * date : 2026-01-16
 * description : 회원가입 요청 DTO
 */
@Getter
@Setter
@Schema(description = "회원가입 요청")
public class CreateUserReqDto {

    @NotBlank(message = "USER001")
    @Email(message = "USER002")
    @Schema(description = "이메일", example = "user@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @NotBlank(message = "USER003")
    @Size(min = 8, max = 100, message = "USER004")
    @Schema(description = "비밀번호 (8자 이상 100자 이하)", example = "password123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @Size(max = 100, message = "USER005")
    @Schema(description = "닉네임 (100자 이하)", example = "홍길동")
    private String nickname;
}

