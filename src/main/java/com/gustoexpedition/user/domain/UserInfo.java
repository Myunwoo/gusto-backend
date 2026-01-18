package com.gustoexpedition.user.domain;

import com.gustoexpedition.user.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * packageName : com.gustoexpedition.user.domain
 * fileName : UserInfo
 * author : fddsg
 * date : 2026-01-16
 * description : JWT에서 추출한 사용자 정보
 */
@Getter
@AllArgsConstructor
public class UserInfo {
  private Long userId;
  private String userNum;
  private String email;
  private UserRole role;
  private Boolean isActive;

  public boolean isAdmin() {
    return role == UserRole.ADMIN;
  }
}
