package com.gustoexpedition.user.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName : com.gustoexpedition.user.adapter.in.dto
 * fileName : UpdateUserReqDto
 * author : fddsg
 * date : 2026-01-16
 * description : 회원정보 수정 요청 DTO
 */
@Getter
@Setter
@Schema(description = "회원정보 수정 요청")
public class UpdateUserReqDto {

  @Size(max = 100, message = "USER005")
  @Schema(description = "닉네임 (100자 이하)", example = "홍길동")
  private String nickname;

  @Size(min = 8, max = 100, message = "USER004")
  @Schema(description = "비밀번호 (8자 이상 100자 이하, 변경하지 않으려면 생략)", example = "newpassword123")
  private String password;
}
