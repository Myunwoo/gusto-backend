package com.gustoexpedition.ingredient.adapter.out.persistence;

import com.gustoexpedition.ingredient.entity.IngredientEdgeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientEdgeJpaRepository extends JpaRepository<IngredientEdgeEntity, Long> {
    
    /**
     * 특정 재료가 from 또는 to로 포함된 모든 관계 조회
     */
    List<IngredientEdgeEntity> findByFromIngredientIdOrToIngredientId(Long fromIngredientId, Long toIngredientId);
}
