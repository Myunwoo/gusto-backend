package com.gustoexpedition.common.entity;

import jakarta.persistence.PreUpdate;
import java.time.Instant;

/**
 * packageName : com.gustoexpedition.common.entity
 * fileName : UpdatedAtListener
 * author : fddsg
 * date : 2026-01-16
 * description : JPA Entity Listener for automatically updating updated_at field
 */
public class UpdatedAtListener {

  @PreUpdate
  public void preUpdate(Object entity) {
    try {
      // updatedAt 필드가 있는지 확인하고 업데이트
      java.lang.reflect.Field updatedAtField = entity.getClass().getDeclaredField("updatedAt");
      updatedAtField.setAccessible(true);
      updatedAtField.set(entity, Instant.now());
    } catch (NoSuchFieldException | IllegalAccessException e) {
      // updatedAt 필드가 없거나 접근할 수 없는 경우 무시
    }
  }
}
