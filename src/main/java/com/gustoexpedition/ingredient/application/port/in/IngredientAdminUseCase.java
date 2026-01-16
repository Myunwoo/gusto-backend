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

    /**
     * methodName : updateIngredient
     * author : fddsg
     * description : 재료 기본정보 수정
     *
     * @param req 재료 기본정보 수정 요청
     * @return 재료 기본정보 수정 응답
     */
    UpdateIngredientBasicResDto updateIngredient(UpdateIngredientBasicReqDto req);

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
     * methodName : deleteIngredient
     * author : fddsg
     * description : 재료 삭제 (관련 데이터도 함께 삭제)
     *
     * @param ingredientId 재료 ID
     * @return 재료 삭제 응답
     */
    DeleteIngredientResDto deleteIngredient(Long ingredientId);

    /**
     * methodName : deleteIngredientI18n
     * author : fddsg
     * description : 재료 locale별 정보 삭제
     *
     * @param ingredientId 재료 ID
     * @param locale 언어 코드
     * @return 재료 locale별 정보 삭제 응답
     */
    DeleteIngredientI18nResDto deleteIngredientI18n(Long ingredientId, String locale);

    /**
     * methodName : deleteAliasAll
     * author : fddsg
     * description : 재료 별칭 일괄 삭제 (특정 locale의 모든 별칭 삭제)
     *
     * @param ingredientId 재료 ID
     * @param locale 언어 코드
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
