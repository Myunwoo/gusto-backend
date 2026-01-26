package com.gustoexpedition.recipe.adapter.out.persistence;

import com.gustoexpedition.recipe.entity.RecipeIngredientEntity;
import com.gustoexpedition.recipe.entity.RecipeIngredientRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecipeIngredientJpaRepository extends JpaRepository<RecipeIngredientEntity, Long> {

  /**
   * 레시피 ID로 모든 재료 조회
   */
  List<RecipeIngredientEntity> findByRecipeId(Long recipeId);

  /**
   * 레시피 ID와 재료 ID로 조회
   */
  Optional<RecipeIngredientEntity> findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

  /**
   * 레시피 ID와 역할로 조회
   */
  List<RecipeIngredientEntity> findByRecipeIdAndRole(Long recipeId, RecipeIngredientRole role);
}
