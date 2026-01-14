package com.gustoexpedition.ingredient.adapter.out.persistence;

import com.gustoexpedition.ingredient.entity.IngredientAliasEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientAliasJpaRepository extends JpaRepository<IngredientAliasEntity, Long> {
}
