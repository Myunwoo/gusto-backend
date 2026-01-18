package com.gustoexpedition.user.adapter.in;

import com.gustoexpedition.user.adapter.in.dto.*;
import com.gustoexpedition.user.application.port.in.UserUseCase;
import com.gustoexpedition.user.domain.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "회원 관리", description = "회원가입 및 본인 정보 관리 API")
public class UserController {
    private final UserUseCase userUseCase;

    @RequestMapping(method = RequestMethod.POST, value = "/signup", produces = { "application/json" })
    @Operation(summary = "회원가입", description = "새로운 회원을 생성합니다. 인증이 필요하지 않습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공", content = @Content(schema = @Schema(implementation = CreateUserResDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 (이메일 중복, 유효성 검증 실패 등)", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    public ResponseEntity<CreateUserResDto> signup(@Valid @RequestBody CreateUserReqDto req) {
        CreateUserResDto res = userUseCase.createUser(req);
        return ResponseEntity.ok(res);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/me", produces = { "application/json" })
    @Operation(summary = "본인 회원정보 조회", description = "로그인한 사용자의 회원정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원정보 조회 성공", content = @Content(schema = @Schema(implementation = SelectUserResDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 (사용자를 찾을 수 없음 등)", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    public ResponseEntity<SelectUserResDto> getMyInfo(HttpServletRequest request) {
        UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");
        SelectUserResDto res = userUseCase.selectMyInfo(userInfo.getUserNum());
        return ResponseEntity.ok(res);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/me", produces = { "application/json" })
    @Operation(summary = "본인 회원정보 수정", description = "로그인한 사용자의 회원정보를 수정합니다. (닉네임, 비밀번호만 수정 가능)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원정보 수정 성공", content = @Content(schema = @Schema(implementation = UpdateUserResDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 (사용자를 찾을 수 없음, 유효성 검증 실패 등)", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    public ResponseEntity<UpdateUserResDto> updateMyInfo(@Valid @RequestBody UpdateUserReqDto req,
            HttpServletRequest request) {
        UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");
        UpdateUserResDto res = userUseCase.updateMyInfo(userInfo.getUserNum(), req);
        return ResponseEntity.ok(res);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/me/delete", produces = { "application/json" })
    @Operation(summary = "회원탈퇴", description = "로그인한 사용자의 계정을 탈퇴 처리합니다 (소프트 삭제: is_active = false).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원탈퇴 성공", content = @Content(schema = @Schema(implementation = DeleteUserResDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 (사용자를 찾을 수 없음 등)", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    public ResponseEntity<DeleteUserResDto> deleteMyAccount(HttpServletRequest request) {
        UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");
        DeleteUserResDto res = userUseCase.deleteMyAccount(userInfo.getUserNum());
        return ResponseEntity.ok(res);
    }
}

