package com.gustoexpedition.ingredient.application.port.in;

import com.gustoexpedition.ingredient.adapter.in.dto.*;

public interface IngredientI18nUseCase {
  /**
   * methodName : updateIngredientI18n
   * author : fddsg
   * description : 재료 locale별 기본정보 저장 (upsert: 없으면 생성, 있으면 수정)
   *
   * @param req 재료 locale별 기본정보 저장 요청
   * @return 재료 locale별 기본정보 저장 응답
   */
  UpdateIngredientI18nResDto updateIngredientI18n(UpdateIngredientI18nReqDto req);

  /**
   * methodName : deleteIngredientI18n
   * author : fddsg
   * description : 재료 locale별 정보 삭제
   *
   * @param ingredientId 재료 ID
   * @param locale       언어 코드
   * @return 재료 locale별 정보 삭제 응답
   */
  DeleteIngredientI18nResDto deleteIngredientI18n(Long ingredientId, String locale);
}
