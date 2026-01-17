package com.gustoexpedition.ingredient.application.port.in;

import com.gustoexpedition.ingredient.adapter.in.dto.*;

public interface IngredientAliasUseCase {
  /**
   * methodName : createAlias
   * author : fddsg
   * description : 재료 별칭 생성 (ingredient_alias 테이블 저장)
   *
   * @param req 재료 별칭 요청
   * @return 재료 별칭 응답
   */
  CreateAliasResDto createAlias(CreateAliasReqDto req);

  /**
   * methodName : updateAliasAll
   * author : fddsg
   * description : 재료 별칭 일괄 수정 (기존 별칭 목록 삭제 후 새 별칭 목록으로 저장)
   *
   * @param req 재료 별칭 일괄 수정 요청
   * @return 재료 별칭 일괄 수정 응답
   */
  UpdateAliasAllResDto updateAliasAll(UpdateAliasAllReqDto req);

  /**
   * methodName : updateAlias
   * author : fddsg
   * description : 재료 별칭 개별 수정
   *
   * @param req 재료 별칭 개별 수정 요청
   * @return 재료 별칭 개별 수정 응답
   */
  UpdateAliasResDto updateAlias(UpdateAliasReqDto req);

  /**
   * methodName : deleteAliasAll
   * author : fddsg
   * description : 재료 별칭 일괄 삭제 (특정 locale의 모든 별칭 삭제)
   *
   * @param ingredientId 재료 ID
   * @param locale       언어 코드
   * @return 재료 별칭 일괄 삭제 응답
   */
  DeleteAliasAllResDto deleteAliasAll(Long ingredientId, String locale);

  /**
   * methodName : deleteAlias
   * author : fddsg
   * description : 재료 별칭 개별 삭제
   *
   * @param aliasId 별칭 ID
   * @return 재료 별칭 개별 삭제 응답
   */
  DeleteAliasResDto deleteAlias(Long aliasId);
}
