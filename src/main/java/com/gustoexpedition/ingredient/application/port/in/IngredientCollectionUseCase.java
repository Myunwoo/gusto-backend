package com.gustoexpedition.ingredient.application.port.in;

import com.gustoexpedition.ingredient.adapter.in.dto.*;

public interface IngredientCollectionUseCase {
  /**
   * methodName : createCollection
   * author : fddsg
   * description : 재료 컬렉션 생성
   *
   * @param req 재료 컬렉션 생성 요청
   * @return 재료 컬렉션 생성 응답
   */
  CreateCollectionResDto createCollection(CreateCollectionReqDto req);

  /**
   * methodName : selectCollectionById
   * author : fddsg
   * description : 재료 컬렉션 조회
   *
   * @param collectionId 컬렉션 ID
   * @return 재료 컬렉션 조회 응답
   */
  SelectCollectionResDto selectCollectionById(Long collectionId);

  /**
   * methodName : updateCollection
   * author : fddsg
   * description : 재료 컬렉션 수정
   *
   * @param req 재료 컬렉션 수정 요청
   * @return 재료 컬렉션 수정 응답
   */
  UpdateCollectionResDto updateCollection(UpdateCollectionReqDto req);

  /**
   * methodName : deleteCollection
   * author : fddsg
   * description : 재료 컬렉션 삭제
   *
   * @param collectionId 컬렉션 ID
   * @return 재료 컬렉션 삭제 응답
   */
  DeleteCollectionResDto deleteCollection(Long collectionId);

  /**
   * methodName : addCollectionItem
   * author : fddsg
   * description : 컬렉션에 재료 추가
   *
   * @param req 컬렉션에 재료 추가 요청
   * @return 컬렉션에 재료 추가 응답
   */
  AddCollectionItemResDto addCollectionItem(AddCollectionItemReqDto req);

  /**
   * methodName : updateCollectionItemOrder
   * author : fddsg
   * description : 컬렉션 아이템 순서 수정
   *
   * @param req 컬렉션 아이템 순서 수정 요청
   * @return 컬렉션 아이템 순서 수정 응답
   */
  UpdateCollectionItemOrderResDto updateCollectionItemOrder(UpdateCollectionItemOrderReqDto req);

  /**
   * methodName : deleteCollectionItem
   * author : fddsg
   * description : 컬렉션 아이템 삭제
   *
   * @param collectionItemId 아이템 ID
   * @return 컬렉션 아이템 삭제 응답
   */
  DeleteCollectionItemResDto deleteCollectionItem(Long collectionItemId);
}
