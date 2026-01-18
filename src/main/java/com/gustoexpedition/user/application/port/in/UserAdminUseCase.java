package com.gustoexpedition.user.application.port.in;

import com.gustoexpedition.user.adapter.in.dto.*;

/**
 * packageName : com.gustoexpedition.user.application.port.in
 * fileName : UserAdminUseCase
 * author : fddsg
 * date : 2026-01-16
 * description : 사용자 관리 어드민용 UseCase
 */
public interface UserAdminUseCase {
  /**
   * 회원가입
   */
  CreateUserResDto createUser(CreateUserReqDto req);

  /**
   * 회원정보 조회
   */
  SelectUserResDto selectUserByUserNum(String userNum);

  /**
   * 회원정보 수정 (어드민용)
   */
  UpdateUserResDto updateUser(UpdateUserAdminReqDto req);

  /**
   * 회원탈퇴 (소프트 삭제: is_active = false)
   */
  DeleteUserResDto deleteUser(String userNum);
}
