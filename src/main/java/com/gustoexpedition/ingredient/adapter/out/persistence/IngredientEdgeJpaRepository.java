package com.gustoexpedition.ingredient.adapter.out.persistence;

import com.gustoexpedition.ingredient.entity.IngredientEdgeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientEdgeJpaRepository extends JpaRepository<IngredientEdgeEntity, Long> {

    /**
     * 특정 재료가 from 또는 to로 포함된 모든 관계 조회
     */
    List<IngredientEdgeEntity> findByFromIngredientIdOrToIngredientId(Long fromIngredientId, Long toIngredientId);

    /**
     * 특정 재료가 포함된 관계를 score 내림차순으로 조회 (마인드맵 트리용)
     */
    List<IngredientEdgeEntity> findByFromIngredientIdOrToIngredientIdOrderByScoreDesc(Long fromIngredientId, Long toIngredientId);
}
