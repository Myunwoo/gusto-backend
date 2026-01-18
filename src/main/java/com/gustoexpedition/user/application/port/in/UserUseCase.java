package com.gustoexpedition.user.application.port.in;

import com.gustoexpedition.user.adapter.in.dto.*;

/**
 * packageName : com.gustoexpedition.user.application.port.in
 * fileName : UserUseCase
 * author : fddsg
 * date : 2026-01-16
 * description : 사용자 관리 UseCase
 */
public interface UserUseCase {
    /**
     * 회원가입
     */
    CreateUserResDto createUser(CreateUserReqDto req);

    /**
     * 본인 회원정보 조회
     */
    SelectUserResDto selectMyInfo(String userNum);

    /**
     * 본인 회원정보 수정
     */
    UpdateUserResDto updateMyInfo(String userNum, UpdateUserReqDto req);

    /**
     * 본인 회원탈퇴 (소프트 삭제: is_active = false)
     */
    DeleteUserResDto deleteMyAccount(String userNum);
}

