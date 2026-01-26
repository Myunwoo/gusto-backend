package com.gustoexpedition.recipe.application.port.in;

import com.gustoexpedition.recipe.adapter.in.dto.*;

import java.util.List;

public interface RecipeAdminUseCase {
    /**
     * methodName : createRecipe
     * author : fddsg
     * description : 레시피 생성
     *
     * @param req 레시피 생성 요청
     * @return 레시피 생성 응답
     */
    CreateRecipeResDto createRecipe(CreateRecipeReqDto req);

    /**
     * methodName : selectRecipeById
     * author : fddsg
     * description : 레시피 조회
     *
     * @param recipeId 레시피 ID
     * @return 레시피 조회 응답
     */
    SelectRecipeResDto selectRecipeById(Long recipeId);

    /**
     * methodName : updateRecipe
     * author : fddsg
     * description : 레시피 수정
     *
     * @param req 레시피 수정 요청
     * @return 레시피 수정 응답
     */
    UpdateRecipeResDto updateRecipe(UpdateRecipeReqDto req);

    /**
     * methodName : deleteRecipe
     * author : fddsg
     * description : 레시피 삭제 (관련 데이터도 함께 삭제)
     *
     * @param recipeId 레시피 ID
     * @return 레시피 삭제 응답
     */
    DeleteRecipeResDto deleteRecipe(Long recipeId);

    /**
     * methodName : createRecipeIngredient
     * author : fddsg
     * description : 레시피 재료 추가
     *
     * @param req 레시피 재료 추가 요청
     * @return 레시피 재료 추가 응답
     */
    CreateRecipeIngredientResDto createRecipeIngredient(CreateRecipeIngredientReqDto req);

    /**
     * methodName : selectRecipeIngredientById
     * author : fddsg
     * description : 레시피 재료 조회
     *
     * @param recipeIngredientId 레시피 재료 ID
     * @return 레시피 재료 조회 응답
     */
    SelectRecipeIngredientResDto selectRecipeIngredientById(Long recipeIngredientId);

    /**
     * methodName : updateRecipeIngredient
     * author : fddsg
     * description : 레시피 재료 수정
     *
     * @param req 레시피 재료 수정 요청
     * @return 레시피 재료 수정 응답
     */
    UpdateRecipeIngredientResDto updateRecipeIngredient(UpdateRecipeIngredientReqDto req);

    /**
     * methodName : deleteRecipeIngredient
     * author : fddsg
     * description : 레시피 재료 삭제
     *
     * @param recipeIngredientId 레시피 재료 ID
     * @return 레시피 재료 삭제 응답
     */
    DeleteRecipeIngredientResDto deleteRecipeIngredient(Long recipeIngredientId);

    /**
     * methodName : selectAll
     * author : fddsg
     * description : 레시피 목록 조회
     *
     * @return 레시피 목록
     */
    List<SelectRecipeListItemDto> selectAll();

    /**
     * methodName : updateRecipeI18n
     * author : fddsg
     * description : 레시피 Locale별 정보 수정 (upsert: 없으면 생성, 있으면 수정)
     *
     * @param req 레시피 Locale별 정보 수정 요청
     * @return 레시피 Locale별 정보 수정 응답
     */
    UpdateRecipeI18nResDto updateRecipeI18n(UpdateRecipeI18nReqDto req);

    /**
     * methodName : selectRecipeIngredientsByRecipeId
     * author : fddsg
     * description : 레시피의 모든 재료 조회
     *
     * @param recipeId 레시피 ID
     * @return 레시피 재료 목록
     */
    List<SelectRecipeIngredientListItemDto> selectRecipeIngredientsByRecipeId(Long recipeId);
}
