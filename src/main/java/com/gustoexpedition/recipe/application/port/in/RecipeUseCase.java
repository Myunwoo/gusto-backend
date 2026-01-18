package com.gustoexpedition.recipe.application.port.in;

import com.gustoexpedition.recipe.adapter.in.dto.RecommendRecipeReqDto;
import com.gustoexpedition.recipe.adapter.in.dto.RecommendRecipeResDto;

/**
 * packageName : com.gustoexpedition.recipe.application.port.in
 * fileName : RecipeUseCase
 * author : fddsg
 * date : 2026-01-16
 * description : 레시피 사용자용 UseCase
 */
public interface RecipeUseCase {
  /**
   * methodName : recommendRecipes
   * author : fddsg
   * description : 사용자가 선택한 재료 목록으로 레시피 추천
   *
   * @param req 레시피 추천 요청 (재료 ID 목록)
   * @return 레시피 추천 응답 (최대 10개, 우선순위 메타데이터 포함)
   */
  RecommendRecipeResDto recommendRecipes(RecommendRecipeReqDto req);
}
