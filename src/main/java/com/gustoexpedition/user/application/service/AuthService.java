package com.gustoexpedition.user.application.service;

import com.gustoexpedition.common.exception.GustoException;
import com.gustoexpedition.user.adapter.in.dto.LoginReqDto;
import com.gustoexpedition.user.adapter.in.dto.LoginResDto;
import com.gustoexpedition.user.adapter.in.dto.LogoutResDto;
import com.gustoexpedition.user.adapter.in.dto.RefreshTokenReqDto;
import com.gustoexpedition.user.adapter.in.dto.RefreshTokenResDto;
import com.gustoexpedition.user.adapter.out.persistence.UserJpaRepository;
import com.gustoexpedition.user.application.port.in.AuthUseCase;
import com.gustoexpedition.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;

/**
 * packageName : com.gustoexpedition.user.application.service
 * fileName : AuthService
 * author : fddsg
 * date : 2026-01-16
 * description : 인증 서비스
 */
@Service
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {

  private final UserJpaRepository userJpaRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  @Override
  @Transactional
  public LoginResDto login(LoginReqDto req) {
    // 1. 사용자 조회
    UserEntity user = userJpaRepository.findByEmail(req.getEmail())
        .orElseThrow(() -> new GustoException("USER012"));

    // 2. 비밀번호 검증
    if (!passwordEncoder.matches(req.getPassword(), user.getPasswordHash())) {
      throw new GustoException("USER012");
    }

    // 3. 활성화 여부 확인
    if (!user.getIsActive()) {
      throw new GustoException("USER013");
    }

    // 4. Access Token 생성
    String accessToken = jwtService.generateAccessToken(user);

    // 5. Refresh Token 생성 및 저장
    String refreshToken = jwtService.generateRefreshToken(user);
    Instant refreshTokenExpiresAt = new Date(System.currentTimeMillis() + jwtService.getRefreshTokenExpiration())
        .toInstant();

    user.setRefreshToken(refreshToken);
    user.setRefreshTokenExpiresAt(refreshTokenExpiresAt);
    userJpaRepository.save(user);

    return new LoginResDto(accessToken, refreshToken, user.getUserNum());
  }

  @Override
  @Transactional
  public RefreshTokenResDto refreshToken(RefreshTokenReqDto req) {
    // 1. Refresh Token 검증
    if (!jwtService.validateToken(req.getRefreshToken())) {
      throw new GustoException("AUTH002");
    }

    // 2. Refresh Token에서 사용자 번호 추출
    String userNum = jwtService.extractUserNumFromRefreshToken(req.getRefreshToken());

    // 3. 사용자 조회 및 Refresh Token 일치 확인
    UserEntity user = userJpaRepository.findByUserNum(userNum)
        .orElseThrow(() -> new GustoException("USER008"));

    if (!req.getRefreshToken().equals(user.getRefreshToken())) {
      throw new GustoException("AUTH002");
    }

    // 4. Refresh Token 만료 확인
    if (user.getRefreshTokenExpiresAt() == null ||
        user.getRefreshTokenExpiresAt().isBefore(Instant.now())) {
      throw new GustoException("AUTH002");
    }

    // 5. 활성화 여부 확인
    if (!user.getIsActive()) {
      throw new GustoException("USER013");
    }

        // 6. 새로운 Access Token 생성
        String newAccessToken = jwtService.generateAccessToken(user);

        return new RefreshTokenResDto(newAccessToken);
    }

    @Override
    @Transactional
    public LogoutResDto logout(String userNum) {
        // 1. 사용자 조회
        UserEntity user = userJpaRepository.findByUserNum(userNum)
                .orElseThrow(() -> new GustoException("USER008"));

        // 2. Refresh Token 무효화 (DB에서 삭제)
        user.setRefreshToken(null);
        user.setRefreshTokenExpiresAt(null);
        userJpaRepository.save(user);

        return new LogoutResDto("로그아웃되었습니다.");
    }
}
