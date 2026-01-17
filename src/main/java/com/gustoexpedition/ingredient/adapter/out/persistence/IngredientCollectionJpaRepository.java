package com.gustoexpedition.ingredient.adapter.out.persistence;

import com.gustoexpedition.ingredient.entity.IngredientCollectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientCollectionJpaRepository extends JpaRepository<IngredientCollectionEntity, Long> {

  /**
   * 제목으로 컬렉션 검색
   */
  List<IngredientCollectionEntity> findByTitleContainingIgnoreCase(String title);
}
