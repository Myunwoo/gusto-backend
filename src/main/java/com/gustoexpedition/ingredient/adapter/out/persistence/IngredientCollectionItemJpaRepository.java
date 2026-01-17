package com.gustoexpedition.ingredient.adapter.out.persistence;

import com.gustoexpedition.ingredient.entity.IngredientCollectionItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IngredientCollectionItemJpaRepository extends JpaRepository<IngredientCollectionItemEntity, Long> {

  /**
   * 컬렉션 ID로 모든 아이템 조회 (추가 순서로 정렬)
   */
  List<IngredientCollectionItemEntity> findByCollectionIdOrderByAddedOrderAsc(Long collectionId);

  /**
   * 재료 ID로 포함된 컬렉션 아이템 조회
   */
  List<IngredientCollectionItemEntity> findByIngredientId(Long ingredientId);

  /**
   * 컬렉션 ID와 재료 ID로 조회
   */
  Optional<IngredientCollectionItemEntity> findByCollectionIdAndIngredientId(Long collectionId, Long ingredientId);
}
