package com.gustoexpedition.ingredient.adapter.out.persistence;

import com.gustoexpedition.ingredient.entity.IngredientAliasEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientAliasJpaRepository extends JpaRepository<IngredientAliasEntity, Long> {
    
    /**
     * 특정 재료의 특정 locale별 별칭 목록 조회
     */
    List<IngredientAliasEntity> findByIngredientIdAndLocale(Long ingredientId, String locale);
    
    /**
     * 특정 재료의 모든 locale별 별칭 목록 조회
     */
    List<IngredientAliasEntity> findByIngredientId(Long ingredientId);
}
