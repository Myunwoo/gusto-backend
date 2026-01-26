package com.gustoexpedition.recipe.adapter.out.persistence;

import com.gustoexpedition.recipe.entity.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeJpaRepository extends JpaRepository<RecipeEntity, Long> {

  /**
   * 제목으로 레시피 검색 (정확히 일치)
   */
  java.util.Optional<RecipeEntity> findByTitle(String title);

  /**
   * 제목으로 레시피 검색 (부분 일치)
   */
  List<RecipeEntity> findByTitleContainingIgnoreCase(String title);

  /**
   * 사용자가 선택한 재료 목록으로 추천 가능한 레시피 조회
   * GIN 인덱스를 활용하여 필수 재료와 사용자 재료가 겹치는 레시피를 빠르게 필터링
   * 
   * @param ingredientIds 사용자가 선택한 재료 ID 배열
   * @return 추천 가능한 레시피 목록 (필수 재료와 사용자 재료가 겹치는 모든 레시피)
   */
  @Query(value = """
      SELECT r.* FROM recipe r
      WHERE r.required_ingredient_ids && CAST(:ingredientIds AS int[])
      ORDER BY
        array_length(r.required_ingredient_ids, 1) DESC,
        array_length(r.optional_ingredient_ids, 1) DESC
      LIMIT 50
      """, nativeQuery = true)
  List<RecipeEntity> findRecommendableRecipes(@Param("ingredientIds") Integer[] ingredientIds);
}
