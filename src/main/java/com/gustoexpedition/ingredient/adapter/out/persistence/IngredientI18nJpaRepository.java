package com.gustoexpedition.ingredient.adapter.out.persistence;

import com.gustoexpedition.ingredient.entity.IngredientI18nEntity;
import com.gustoexpedition.ingredient.entity.IngredientI18nId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IngredientI18nJpaRepository extends JpaRepository<IngredientI18nEntity, IngredientI18nId> {
    Optional<IngredientI18nEntity> findByLocaleAndName(String locale, String name);
    
    /**
     * 특정 재료의 모든 locale 정보 조회
     */
    List<IngredientI18nEntity> findByIngredientId(Long ingredientId);
}
