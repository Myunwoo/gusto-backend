package com.gustoexpedition.user.adapter.in;

import com.gustoexpedition.common.annotation.RequireAdmin;
import com.gustoexpedition.user.adapter.in.dto.*;
import com.gustoexpedition.user.application.port.in.UserAdminUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/api/admin/user")
@RequiredArgsConstructor
@RequireAdmin
@Tag(name = "회원 관리 ADMIN", description = "회원 생성 및 조회 API")
public class UserAdminController {
  private final UserAdminUseCase userAdminUseCase;

  @RequestMapping(method = RequestMethod.POST, value = "/createUser", produces = { "application/json" })
  @Operation(summary = "회원가입", description = "새로운 회원을 생성합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "회원가입 성공", content = @Content(schema = @Schema(implementation = CreateUserResDto.class))),
      @ApiResponse(responseCode = "400", description = "잘못된 요청 (이메일 중복, 유효성 검증 실패 등)", content = @Content),
      @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
  })
  public ResponseEntity<CreateUserResDto> createUser(@Valid @RequestBody CreateUserReqDto req) {
    CreateUserResDto res = userAdminUseCase.createUser(req);
    return ResponseEntity.ok(res);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/selectUserByUserNum", produces = { "application/json" })
  @Operation(summary = "회원정보 조회", description = "사용자 번호로 회원정보를 조회합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "회원정보 조회 성공", content = @Content(schema = @Schema(implementation = SelectUserResDto.class))),
      @ApiResponse(responseCode = "400", description = "잘못된 요청 (사용자를 찾을 수 없음 등)", content = @Content),
      @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
  })
  public ResponseEntity<SelectUserResDto> selectUserByUserNum(
      @Parameter(description = "사용자 번호 (8자리)", required = true, example = "12345678") @RequestParam("userNum") String userNum) {
    SelectUserResDto res = userAdminUseCase.selectUserByUserNum(userNum);
    return ResponseEntity.ok(res);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/updateUser", produces = { "application/json" })
  @Operation(summary = "회원정보 수정", description = "회원정보를 수정합니다. (어드민용: 역할, 활성화 여부 수정 가능)")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "회원정보 수정 성공", content = @Content(schema = @Schema(implementation = UpdateUserResDto.class))),
      @ApiResponse(responseCode = "400", description = "잘못된 요청 (사용자를 찾을 수 없음, 유효성 검증 실패 등)", content = @Content),
      @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
  })
  public ResponseEntity<UpdateUserResDto> updateUser(@Valid @RequestBody UpdateUserAdminReqDto req) {
    UpdateUserResDto res = userAdminUseCase.updateUser(req);
    return ResponseEntity.ok(res);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/deleteUser", produces = { "application/json" })
  @Operation(summary = "회원탈퇴", description = "회원을 탈퇴 처리합니다 (소프트 삭제: is_active = false).")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "회원탈퇴 성공", content = @Content(schema = @Schema(implementation = DeleteUserResDto.class))),
      @ApiResponse(responseCode = "400", description = "잘못된 요청 (사용자를 찾을 수 없음 등)", content = @Content),
      @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
  })
  public ResponseEntity<DeleteUserResDto> deleteUser(
      @Parameter(description = "사용자 번호 (8자리)", required = true, example = "12345678") @RequestParam("userNum") String userNum) {
    DeleteUserResDto res = userAdminUseCase.deleteUser(userNum);
    return ResponseEntity.ok(res);
  }
}
