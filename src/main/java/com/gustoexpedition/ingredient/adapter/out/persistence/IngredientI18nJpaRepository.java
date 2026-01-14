package com.gustoexpedition.ingredient.adapter.out.persistence;

import com.gustoexpedition.ingredient.entity.IngredientI18nEntity;
import com.gustoexpedition.ingredient.entity.IngredientI18nId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngredientI18nJpaRepository extends JpaRepository<IngredientI18nEntity, IngredientI18nId> {
    Optional<IngredientI18nEntity> findByLocaleAndName(String locale, String name);
}
