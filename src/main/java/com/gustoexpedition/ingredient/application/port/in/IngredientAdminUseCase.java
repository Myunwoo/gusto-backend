package com.gustoexpedition.ingredient.application.port.in;

import com.gustoexpedition.ingredient.adapter.in.dto.*;

import java.util.List;

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
     * methodName : selectById
     * author : fddsg
     * description : 재료 조회
     *
     * @param ingredientId      재료 ID
     * @param locale            언어 코드
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
     * methodName : deleteIngredient
     * author : fddsg
     * description : 재료 삭제 (관련 데이터도 함께 삭제)
     *
     * @param ingredientId 재료 ID
     * @return 재료 삭제 응답
     */
    DeleteIngredientResDto deleteIngredient(Long ingredientId);

    /**
     * methodName : selectAll
     * author : fddsg
     * description : 재료 목록 조회
     *
     * @return 재료 목록
     */
    List<SelectIngredientListItemDto> selectAll();

}
