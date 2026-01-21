package com.gustoexpedition.ingredient.adapter.out.persistence;

import com.gustoexpedition.ingredient.entity.IngredientEntity;
import com.gustoexpedition.ingredient.entity.IngredientI18nEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngredientJpaRepository extends JpaRepository<IngredientEntity, Long> {
    Optional<IngredientEntity> findByName(String name);
}
