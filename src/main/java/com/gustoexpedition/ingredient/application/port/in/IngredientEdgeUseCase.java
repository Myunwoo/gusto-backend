package com.gustoexpedition.ingredient.application.port.in;

import com.gustoexpedition.ingredient.adapter.in.dto.*;

public interface IngredientEdgeUseCase {
  /**
   * methodName : createEdge
   * author : fddsg
   * description : 재료 간 관계 생성
   *
   * @param req 재료 간 관계 생성 요청
   * @return 재료 간 관계 생성 응답
   */
  CreateEdgeResDto createEdge(CreateEdgeReqDto req);

  /**
   * methodName : selectEdgeById
   * author : fddsg
   * description : 재료 간 관계 조회
   *
   * @param edgeId 관계 ID
   * @return 재료 간 관계 조회 응답
   */
  SelectEdgeResDto selectEdgeById(Long edgeId);

  /**
   * methodName : updateEdge
   * author : fddsg
   * description : 재료 간 관계 수정
   *
   * @param req 재료 간 관계 수정 요청
   * @return 재료 간 관계 수정 응답
   */
  UpdateEdgeResDto updateEdge(UpdateEdgeReqDto req);

  /**
   * methodName : deleteEdge
   * author : fddsg
   * description : 재료 간 관계 삭제
   *
   * @param edgeId 관계 ID
   * @return 재료 간 관계 삭제 응답
   */
  DeleteEdgeResDto deleteEdge(Long edgeId);
}
