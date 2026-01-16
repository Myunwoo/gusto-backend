package com.gustoexpedition.ingredient.application.port.in;

import com.gustoexpedition.ingredient.adapter.in.dto.*;

public interface IngredientAdminUseCase {
    /**
     * methodName : createIngredient
     * author : fddsg
     * description : 재료 기본정보 생성
     *
     * @param req 재료 기본정보 요청
     * @return 재료 기본정보 응답
     */
    CreateIngredientBasicResDto createIngredient(CreateIngredientBasicReqDto req);

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
     * methodName : createAlias
     * author : fddsg
     * description : 재료 별칭 생성 (ingredient_alias 테이블 저장)
     *
     * @param req 재료 별칭 요청
     * @return 재료 별칭 응답
     */
    CreateAliasResDto createAlias(CreateAliasReqDto req);

    /**
     * methodName : selectById
     * author : fddsg
     * description : 재료 조회
     *
     * @param ingredientId 재료 ID
     * @param locale 언어 코드
     * @param includeRelationYn 관계 정보 포함 여부
     * @return 재료 조회 응답
     */
    SelectIngredientResDto selectById(Long ingredientId, String locale, Boolean includeRelationYn);
}
