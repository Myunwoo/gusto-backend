package com.gustoexpedition.user.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName : com.gustoexpedition.user.adapter.in.dto
 * fileName : LoginReqDto
 * author : fddsg
 * date : 2026-01-16
 * description : 로그인 요청 DTO
 */
@Getter
@Setter
@Schema(description = "로그인 요청")
public class LoginReqDto {

  @NotBlank(message = "USER001")
  @Email(message = "USER002")
  @Schema(description = "이메일", example = "user@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
  private String email;

  @NotBlank(message = "USER003")
  @Schema(description = "비밀번호", example = "password123", requiredMode = Schema.RequiredMode.REQUIRED)
  private String password;
}
