package com.gustoexpedition.recipe.adapter.out.persistence;

import com.gustoexpedition.recipe.entity.RecipeAliasEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeAliasJpaRepository extends JpaRepository<RecipeAliasEntity, Long> {

  /**
   * 특정 레시피의 특정 locale별 별칭 목록 조회
   */
  List<RecipeAliasEntity> findByRecipeIdAndLocale(Long recipeId, String locale);

  /**
   * 특정 레시피의 모든 locale별 별칭 목록 조회
   */
  List<RecipeAliasEntity> findByRecipeId(Long recipeId);
}
