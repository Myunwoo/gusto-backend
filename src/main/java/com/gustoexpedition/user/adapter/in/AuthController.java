package com.gustoexpedition.user.adapter.in;

import com.gustoexpedition.user.adapter.in.dto.LoginReqDto;
import com.gustoexpedition.user.adapter.in.dto.LoginResDto;
import com.gustoexpedition.user.adapter.in.dto.LogoutResDto;
import com.gustoexpedition.user.adapter.in.dto.RefreshTokenReqDto;
import com.gustoexpedition.user.adapter.in.dto.RefreshTokenResDto;
import com.gustoexpedition.user.application.port.in.AuthUseCase;
import com.gustoexpedition.user.domain.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "인증", description = "로그인 및 토큰 갱신 API")
public class AuthController {
  private final AuthUseCase authUseCase;

  @RequestMapping(method = RequestMethod.POST, value = "/login", produces = { "application/json" })
  @Operation(summary = "로그인", description = "이메일과 비밀번호로 로그인하여 Access Token과 Refresh Token을 발급받습니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(schema = @Schema(implementation = LoginResDto.class))),
      @ApiResponse(responseCode = "400", description = "잘못된 요청 (이메일/비밀번호 불일치, 유효성 검증 실패 등)", content = @Content),
      @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
  })
  public ResponseEntity<LoginResDto> login(@Valid @RequestBody LoginReqDto req) {
    LoginResDto res = authUseCase.login(req);
    return ResponseEntity.ok(res);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/refresh", produces = { "application/json" })
  @Operation(summary = "토큰 갱신", description = "Refresh Token을 사용하여 새로운 Access Token을 발급받습니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "토큰 갱신 성공", content = @Content(schema = @Schema(implementation = RefreshTokenResDto.class))),
      @ApiResponse(responseCode = "400", description = "잘못된 요청 (유효하지 않은 Refresh Token 등)", content = @Content),
      @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
  })
  public ResponseEntity<RefreshTokenResDto> refreshToken(@Valid @RequestBody RefreshTokenReqDto req) {
    RefreshTokenResDto res = authUseCase.refreshToken(req);
    return ResponseEntity.ok(res);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/logout", produces = { "application/json" })
  @Operation(summary = "로그아웃", description = "로그아웃하여 Refresh Token을 무효화합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "로그아웃 성공", content = @Content(schema = @Schema(implementation = LogoutResDto.class))),
      @ApiResponse(responseCode = "400", description = "잘못된 요청 (사용자를 찾을 수 없음 등)", content = @Content),
      @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
  })
  public ResponseEntity<LogoutResDto> logout(HttpServletRequest request) {
    UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");
    LogoutResDto res = authUseCase.logout(userInfo.getUserNum());
    return ResponseEntity.ok(res);
  }
}
