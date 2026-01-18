package com.gustoexpedition.user.application.service;

import com.gustoexpedition.user.domain.UserInfo;
import com.gustoexpedition.user.entity.UserEntity;
import com.gustoexpedition.user.entity.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * packageName : com.gustoexpedition.user.application.service
 * fileName : JwtService
 * author : fddsg
 * date : 2026-01-16
 * description : JWT 토큰 생성 및 검증 서비스
 */
@Slf4j
@Service
public class JwtService {

  private final SecretKey secretKey;
  private final long accessTokenExpiration;
  private final long refreshTokenExpiration;

  public JwtService(
      @Value("${gusto.security.jwt.secret}") String secret,
      @Value("${gusto.security.jwt.access-token-expiration}") long accessTokenExpiration,
      @Value("${gusto.security.jwt.refresh-token-expiration}") long refreshTokenExpiration) {
    this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    this.accessTokenExpiration = accessTokenExpiration;
    this.refreshTokenExpiration = refreshTokenExpiration;
  }

  /**
   * Access Token 생성
   */
  public String generateAccessToken(UserEntity user) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + accessTokenExpiration);

    return Jwts.builder()
        .subject(user.getUserNum())
        .claim("userId", user.getUserId())
        .claim("email", user.getEmail())
        .claim("role", user.getRole().name())
        .claim("isActive", user.getIsActive())
        .issuedAt(now)
        .expiration(expiryDate)
        .signWith(secretKey)
        .compact();
  }

  /**
   * Refresh Token 생성
   */
  public String generateRefreshToken(UserEntity user) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + refreshTokenExpiration);

    return Jwts.builder()
        .subject(user.getUserNum())
        .claim("userId", user.getUserId())
        .claim("type", "refresh")
        .issuedAt(now)
        .expiration(expiryDate)
        .signWith(secretKey)
        .compact();
  }

  /**
   * 토큰에서 사용자 정보 추출
   */
  public UserInfo extractUserInfo(String token) {
    try {
      Claims claims = Jwts.parser()
          .verifyWith(secretKey)
          .build()
          .parseSignedClaims(token)
          .getPayload();

      Long userId = claims.get("userId", Long.class);
      String userNum = claims.getSubject();
      String email = claims.get("email", String.class);
      String roleStr = claims.get("role", String.class);
      Boolean isActive = claims.get("isActive", Boolean.class);

      UserRole role = roleStr != null ? UserRole.valueOf(roleStr) : UserRole.USER;

      return new UserInfo(userId, userNum, email, role, isActive);
    } catch (Exception e) {
      log.warn("JWT 토큰 파싱 실패: {}", e.getMessage());
      throw new RuntimeException("유효하지 않은 토큰입니다", e);
    }
  }

  /**
   * 토큰 유효성 검증
   */
  public boolean validateToken(String token) {
    try {
      Jwts.parser()
          .verifyWith(secretKey)
          .build()
          .parseSignedClaims(token);
      return true;
    } catch (Exception e) {
      log.debug("토큰 검증 실패: {}", e.getMessage());
      return false;
    }
  }

  /**
   * Refresh Token에서 사용자 번호 추출
   */
  public String extractUserNumFromRefreshToken(String refreshToken) {
    try {
      Claims claims = Jwts.parser()
          .verifyWith(secretKey)
          .build()
          .parseSignedClaims(refreshToken)
          .getPayload();

      String type = claims.get("type", String.class);
      if (!"refresh".equals(type)) {
        throw new RuntimeException("Refresh Token이 아닙니다");
      }

      return claims.getSubject();
    } catch (Exception e) {
      log.warn("Refresh Token 파싱 실패: {}", e.getMessage());
      throw new RuntimeException("유효하지 않은 Refresh Token입니다", e);
    }
  }

  /**
   * 토큰 만료 시간 확인
   */
  public boolean isTokenExpired(String token) {
    try {
      Claims claims = Jwts.parser()
          .verifyWith(secretKey)
          .build()
          .parseSignedClaims(token)
          .getPayload();

      return claims.getExpiration().before(new Date());
    } catch (Exception e) {
      return true;
    }
  }

  /**
   * Refresh Token 만료 시간 반환
   */
  public long getRefreshTokenExpiration() {
    return refreshTokenExpiration;
  }
}
