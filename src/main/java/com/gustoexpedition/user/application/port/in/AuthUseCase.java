package com.gustoexpedition.user.application.port.in;

import com.gustoexpedition.user.adapter.in.dto.LoginReqDto;
import com.gustoexpedition.user.adapter.in.dto.LoginResDto;
import com.gustoexpedition.user.adapter.in.dto.LogoutResDto;
import com.gustoexpedition.user.adapter.in.dto.RefreshTokenReqDto;
import com.gustoexpedition.user.adapter.in.dto.RefreshTokenResDto;

/**
 * packageName : com.gustoexpedition.user.application.port.in
 * fileName : AuthUseCase
 * author : fddsg
 * date : 2026-01-16
 * description : 인증 관련 UseCase
 */
public interface AuthUseCase {
    /**
     * 로그인 (Access Token + Refresh Token 발급)
     */
    LoginResDto login(LoginReqDto req);

    /**
     * Refresh Token으로 Access Token 갱신
     */
    RefreshTokenResDto refreshToken(RefreshTokenReqDto req);

    /**
     * 로그아웃 (Refresh Token 무효화)
     */
    LogoutResDto logout(String userNum);
}
