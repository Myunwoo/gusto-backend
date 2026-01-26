package com.gustoexpedition.recipe.application.port.in;

import com.gustoexpedition.recipe.adapter.in.dto.*;

public interface RecipeAliasUseCase {
  /**
   * methodName : createAlias
   * author : fddsg
   * description : 레시피 별칭 생성 (recipe_alias 테이블 저장)
   *
   * @param req 레시피 별칭 요청
   * @return 레시피 별칭 응답
   */
  CreateAliasResDto createAlias(CreateAliasReqDto req);

  /**
   * methodName : updateAliasAll
   * author : fddsg
   * description : 레시피 별칭 일괄 수정 (기존 별칭 목록 삭제 후 새 별칭 목록으로 저장)
   *
   * @param req 레시피 별칭 일괄 수정 요청
   * @return 레시피 별칭 일괄 수정 응답
   */
  UpdateAliasAllResDto updateAliasAll(UpdateAliasAllReqDto req);

  /**
   * methodName : updateAlias
   * author : fddsg
   * description : 레시피 별칭 개별 수정
   *
   * @param req 레시피 별칭 개별 수정 요청
   * @return 레시피 별칭 개별 수정 응답
   */
  UpdateAliasResDto updateAlias(UpdateAliasReqDto req);

  /**
   * methodName : deleteAliasAll
   * author : fddsg
   * description : 레시피 별칭 일괄 삭제 (특정 locale의 모든 별칭 삭제)
   *
   * @param recipeId 레시피 ID
   * @param locale       언어 코드
   * @return 레시피 별칭 일괄 삭제 응답
   */
  DeleteAliasAllResDto deleteAliasAll(Long recipeId, String locale);

  /**
   * methodName : deleteAlias
   * author : fddsg
   * description : 레시피 별칭 개별 삭제
   *
   * @param aliasId 레시피 별칭 ID
   * @return 레시피 별칭 개별 삭제 응답
   */
  DeleteAliasResDto deleteAlias(Long aliasId);
}

