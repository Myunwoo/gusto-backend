package com.gustoexpedition.ingredient.adapter.out.persistence;

import com.gustoexpedition.ingredient.entity.IngredientEdgeEvidenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientEdgeEvidenceJpaRepository extends JpaRepository<IngredientEdgeEvidenceEntity, Long> {

  /**
   * 특정 edge에 속한 모든 증거 조회
   */
  List<IngredientEdgeEvidenceEntity> findByEdgeId(Long edgeId);

  /**
   * 특정 edge의 특정 타입 증거 조회
   */
  List<IngredientEdgeEvidenceEntity> findByEdgeIdAndEvidenceType(Long edgeId,
      IngredientEdgeEvidenceEntity.EdgeEvidenceType evidenceType);
}
