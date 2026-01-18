package com.gustoexpedition.user.entity;

import com.gustoexpedition.common.entity.UpdatedAtListener;
import jakarta.persistence.*;

import java.time.Instant;

/**
 * packageName : com.gustoexpedition.user.entity
 * fileName : UserEntity
 * author : fddsg
 * date : 2026-01-16
 * description : 사용자 엔티티
 */
@Entity
@Table(name = "user")
@EntityListeners(UpdatedAtListener.class)
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long userId;

  @Column(name = "user_num", nullable = false, unique = true, columnDefinition = "bpchar(8)")
  private String userNum;

  @Column(name = "email", nullable = false, unique = true, length = 255)
  private String email;

  @Column(name = "password_hash", nullable = false, length = 255)
  private String passwordHash;

  @Column(name = "nickname", length = 100)
  private String nickname;

  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false)
  private UserRole role = UserRole.USER;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive = true;

  @Column(name = "refresh_token", length = 500)
  private String refreshToken;

  @Column(name = "refresh_token_expires_at")
  private Instant refreshTokenExpiresAt;

  @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
  private Instant createdAt;

  @Column(name = "updated_at", nullable = false, insertable = false, updatable = true)
  private Instant updatedAt;

  protected UserEntity() {
  }

  public UserEntity(String userNum, String email, String passwordHash, String nickname, UserRole role) {
    this.userNum = userNum;
    this.email = email;
    this.passwordHash = passwordHash;
    this.nickname = nickname;
    this.role = role != null ? role : UserRole.USER;
    this.isActive = true;
  }

  public Long getUserId() {
    return userId;
  }

  public String getUserNum() {
    return userNum;
  }

  public String getEmail() {
    return email;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public String getNickname() {
    return nickname;
  }

  public UserRole getRole() {
    return role;
  }

  public Boolean getIsActive() {
    return isActive;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public Instant getRefreshTokenExpiresAt() {
    return refreshTokenExpiresAt;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public void setUserNum(String userNum) {
    this.userNum = userNum;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public void setRole(UserRole role) {
    this.role = role;
  }

  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public void setRefreshTokenExpiresAt(Instant refreshTokenExpiresAt) {
    this.refreshTokenExpiresAt = refreshTokenExpiresAt;
  }
}
