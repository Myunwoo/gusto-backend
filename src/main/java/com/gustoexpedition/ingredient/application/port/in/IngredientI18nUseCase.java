package com.gustoexpedition.ingredient.application.port.in;

import com.gustoexpedition.ingredient.adapter.in.dto.*;

public interface IngredientI18nUseCase {
  /**
   * methodName : createIngredientI18n
   * author : fddsg
   * description : 재료 locale별 기본정보 생성 (ingredient_i18n 테이블 저장)
   *
   * @param req 재료 locale별 기본정보 요청
   * @return 재료 locale별 기본정보 응답
   */
  CreateIngredientI18nResDto createIngredientI18n(CreateIngredientI18nReqDto req);

  /**
   * methodName : updateIngredientI18n
   * author : fddsg
   * description : 재료 locale별 기본정보 수정
   *
   * @param req 재료 locale별 기본정보 수정 요청
   * @return 재료 locale별 기본정보 수정 응답
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
