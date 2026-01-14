package com.gustoexpedition.ingredient.adapter.out.persistence;

import com.gustoexpedition.ingredient.entity.IngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientJpaRepository extends JpaRepository<IngredientEntity, Long> {
}
