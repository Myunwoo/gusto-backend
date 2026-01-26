package com.gustoexpedition.recipe.adapter.out.persistence;

import com.gustoexpedition.recipe.entity.RecipeI18nEntity;
import com.gustoexpedition.recipe.entity.RecipeI18nId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecipeI18nJpaRepository extends JpaRepository<RecipeI18nEntity, RecipeI18nId> {

  /**
   * 레시피 ID로 모든 locale 정보 조회
   */
  List<RecipeI18nEntity> findByRecipeId(Long recipeId);

  /**
   * 레시피 ID와 locale로 조회
   */
  Optional<RecipeI18nEntity> findByRecipeIdAndLocale(Long recipeId, String locale);
}

