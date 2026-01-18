package com.gustoexpedition.user.application.service;

import com.gustoexpedition.common.exception.GustoException;
import com.gustoexpedition.user.adapter.in.dto.*;
import com.gustoexpedition.user.adapter.out.persistence.UserJpaRepository;
import com.gustoexpedition.user.application.port.in.UserUseCase;
import com.gustoexpedition.user.entity.UserEntity;
import com.gustoexpedition.user.entity.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * packageName : com.gustoexpedition.user.application.service
 * fileName : UserService
 * author : fddsg
 * date : 2026-01-16
 * description : 사용자 관리 서비스
 */
@Service
@RequiredArgsConstructor
public class UserService implements UserUseCase {

  private final UserJpaRepository userJpaRepository;
  private final PasswordEncoder passwordEncoder;
  private static final int USER_NUM_LENGTH = 8;
  private static final Random random = new Random();

  @Override
  @Transactional
  public CreateUserResDto createUser(CreateUserReqDto req) {
    // 이메일 중복 체크
    if (userJpaRepository.existsByEmail(req.getEmail())) {
      throw new GustoException("USER007");
    }

    // user_num 생성 (8자리, 중복 체크)
    String userNum = generateUniqueUserNum();

    // 비밀번호 해시화
    String passwordHash = passwordEncoder.encode(req.getPassword());

    // 사용자 생성
    UserEntity user = new UserEntity(
        userNum,
        req.getEmail(),
        passwordHash,
        req.getNickname(),
        UserRole.USER);

    UserEntity savedUser = userJpaRepository.save(user);

    return new CreateUserResDto(savedUser.getUserNum());
  }

  @Override
  @Transactional(readOnly = true)
  public SelectUserResDto selectMyInfo(String userNum) {
    UserEntity user = userJpaRepository.findByUserNum(userNum)
        .orElseThrow(() -> new GustoException("USER008"));

    return new SelectUserResDto(
        user.getUserNum(),
        user.getEmail(),
        user.getNickname(),
        user.getRole().name(),
        user.getIsActive(),
        user.getCreatedAt().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_INSTANT));
  }

  @Override
  @Transactional
  public UpdateUserResDto updateMyInfo(String userNum, UpdateUserReqDto req) {
    UserEntity user = userJpaRepository.findByUserNum(userNum)
        .orElseThrow(() -> new GustoException("USER008"));

    // 닉네임 수정
    if (req.getNickname() != null) {
      user.setNickname(req.getNickname());
    }

    // 비밀번호 수정 (제공된 경우에만)
    if (req.getPassword() != null && !req.getPassword().isEmpty()) {
      user.setPasswordHash(passwordEncoder.encode(req.getPassword()));
    }

    // 역할 수정은 본인이 할 수 없음 (어드민만 가능)
    // 활성화 여부 수정도 본인이 할 수 없음

    userJpaRepository.save(user);

    return new UpdateUserResDto(user.getUserNum());
  }

  @Override
  @Transactional
  public DeleteUserResDto deleteMyAccount(String userNum) {
    UserEntity user = userJpaRepository.findByUserNum(userNum)
        .orElseThrow(() -> new GustoException("USER008"));

    // 소프트 삭제: is_active = false
    user.setIsActive(false);

    // Refresh Token도 무효화
    user.setRefreshToken(null);
    user.setRefreshTokenExpiresAt(null);

    userJpaRepository.save(user);

    return new DeleteUserResDto(user.getUserNum());
  }

  /**
   * 고유한 8자리 user_num 생성
   */
  private String generateUniqueUserNum() {
    int maxAttempts = 100;
    for (int i = 0; i < maxAttempts; i++) {
      String userNum = generateRandomUserNum();
      if (!userJpaRepository.existsByUserNum(userNum)) {
        return userNum;
      }
    }
    throw new GustoException("USER010");
  }

  /**
   * 랜덤 8자리 숫자 문자열 생성
   */
  private String generateRandomUserNum() {
    int min = 10000000; // 8자리 최소값
    int max = 99999999; // 8자리 최대값
    int num = random.nextInt(max - min + 1) + min;
    return String.valueOf(num);
  }
}
