package com.gustoexpedition.ingredient.application.port.in;

import com.gustoexpedition.ingredient.adapter.in.dto.*;

public interface IngredientEvidenceUseCase {
  /**
   * methodName : createEvidence
   * author : fddsg
   * description : 재료 간 관계 증거 생성
   *
   * @param req 재료 간 관계 증거 생성 요청
   * @return 재료 간 관계 증거 생성 응답
   */
  CreateEvidenceResDto createEvidence(CreateEvidenceReqDto req);

  /**
   * methodName : updateEvidence
   * author : fddsg
   * description : 재료 간 관계 증거 수정
   *
   * @param req 재료 간 관계 증거 수정 요청
   * @return 재료 간 관계 증거 수정 응답
   */
  UpdateEvidenceResDto updateEvidence(UpdateEvidenceReqDto req);

  /**
   * methodName : deleteEvidence
   * author : fddsg
   * description : 재료 간 관계 증거 삭제
   *
   * @param evidenceId 증거 ID
   * @return 재료 간 관계 증거 삭제 응답
   */
  DeleteEvidenceResDto deleteEvidence(Long evidenceId);
}
