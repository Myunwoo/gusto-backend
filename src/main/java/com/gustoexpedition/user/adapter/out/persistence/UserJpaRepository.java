package com.gustoexpedition.user.adapter.out.persistence;

import com.gustoexpedition.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
  Optional<UserEntity> findByUserNum(String userNum);

  Optional<UserEntity> findByEmail(String email);

  boolean existsByUserNum(String userNum);

  boolean existsByEmail(String email);
}
