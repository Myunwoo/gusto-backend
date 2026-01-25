package com.gustoexpedition.ingredient.application.service;

import com.gustoexpedition.common.exception.GustoException;
import com.gustoexpedition.ingredient.adapter.in.dto.*;
import com.gustoexpedition.ingredient.adapter.out.persistence.IngredientEdgeEvidenceJpaRepository;
import com.gustoexpedition.ingredient.adapter.out.persistence.IngredientEdgeJpaRepository;
import com.gustoexpedition.ingredient.adapter.out.persistence.IngredientJpaRepository;
import com.gustoexpedition.ingredient.application.port.in.IngredientEdgeUseCase;
import com.gustoexpedition.ingredient.entity.IngredientEdgeEntity;
import com.gustoexpedition.ingredient.entity.IngredientEdgeEvidenceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * packageName : com.gustoexpedition.ingredient.application.service
 * fileName : IngredientEdgeService
 * author : fddsg
 * date : 2026-01-16
 * description : 재료 간 관계 관리 서비스
 */
@Service
@RequiredArgsConstructor
public class IngredientEdgeService implements IngredientEdgeUseCase {

  private final IngredientJpaRepository ingredientJpaRepository;
  private final IngredientEdgeJpaRepository ingredientEdgeJpaRepository;
  private final IngredientEdgeEvidenceJpaRepository ingredientEdgeEvidenceJpaRepository;

  @Override
  @Transactional
  public CreateEdgeResDto createEdge(CreateEdgeReqDto req) {
    // 1. 재료 존재 확인
    ingredientJpaRepository.findById(req.getFromIngredientId())
        .orElseThrow(() -> new GustoException("INGR006")); // 재료를 찾을 수 없습니다.
    ingredientJpaRepository.findById(req.getToIngredientId())
        .orElseThrow(() -> new GustoException("INGR006")); // 재료를 찾을 수 없습니다.

    // 2. 동일한 재료 간 관계인지 확인
    if (req.getFromIngredientId().equals(req.getToIngredientId())) {
      throw new GustoException("EDGE009"); // 동일한 재료 간에는 관계를 생성할 수 없습니다.
    }

    // 3. 이미 존재하는 관계인지 확인 (무방향 그래프이므로 양방향 확인)
    Long minId = Math.min(req.getFromIngredientId(), req.getToIngredientId());
    Long maxId = Math.max(req.getFromIngredientId(), req.getToIngredientId());

    List<IngredientEdgeEntity> existingEdges = ingredientEdgeJpaRepository
        .findByFromIngredientIdOrToIngredientId(req.getFromIngredientId(), req.getToIngredientId());

    boolean alreadyExists = existingEdges.stream()
        .anyMatch(edge -> (edge.getFromIngredientId().equals(minId) && edge.getToIngredientId().equals(maxId)) ||
            (edge.getFromIngredientId().equals(maxId) && edge.getToIngredientId().equals(minId)));

    if (alreadyExists) {
      throw new GustoException("EDGE010"); // 이미 존재하는 관계입니다.
    }

    // 4. 관계 타입 검증
    IngredientEdgeEntity.IngredientRelationType relationType = req.getRelationType();
    if (relationType == null) {
      throw new GustoException("EDGE003"); // 관계 타입은 필수입니다.
    }
    // Enum 값이 유효한지 확인 (PAIR_WELL, AVOID, NEUTRAL 중 하나인지)
    boolean isValidType = false;
    for (IngredientEdgeEntity.IngredientRelationType validType : IngredientEdgeEntity.IngredientRelationType.values()) {
      if (validType == relationType) {
        isValidType = true;
        break;
      }
    }
    if (!isValidType) {
      throw new GustoException("EDGE003"); // 유효하지 않은 관계 타입입니다.
    }

    // 5. 점수 검증
    Integer score;

    if (relationType == IngredientEdgeEntity.IngredientRelationType.NEUTRAL) {
      // NEUTRAL은 점수가 없어도 됨 (0으로 설정)
      score = 0;
    } else {
      // PAIR_WELL 또는 AVOID는 점수 필수
      if (req.getScore() == null) {
        throw new GustoException("EDGE004"); // 관계 점수는 필수입니다.
      }

      score = req.getScore();
      if (score < 1 || score > 10) {
        throw new GustoException("EDGE005"); // 관계 점수는 1-10 사이여야 합니다.
      }
    }

    // 5. Edge 엔티티 생성 및 저장 (생성자에서 자동으로 정규화)
    IngredientEdgeEntity edgeEntity = new IngredientEdgeEntity(
        req.getFromIngredientId(),
        req.getToIngredientId(),
        relationType,
        score,
        req.getConfidence(),
        req.getReasonSummary());
    IngredientEdgeEntity savedEdge = ingredientEdgeJpaRepository.save(edgeEntity);

    // 5. 응답 DTO 생성
    return new CreateEdgeResDto(
        savedEdge.getEdgeId(),
        savedEdge.getFromIngredientId(),
        savedEdge.getToIngredientId(),
        savedEdge.getRelationType(),
        savedEdge.getScore(),
        savedEdge.getConfidence(),
        savedEdge.getReasonSummary(),
        savedEdge.getCreatedAt());
  }

  @Override
  @Transactional(readOnly = true)
  public SelectEdgeResDto selectEdgeById(Long edgeId) {
    // 1. Edge 조회
    IngredientEdgeEntity edge = ingredientEdgeJpaRepository.findById(edgeId)
        .orElse(null);

    if (edge == null) {
      return null;
    }

    // 2. 증거 목록 조회
    List<IngredientEdgeEvidenceEntity> evidenceEntities = ingredientEdgeEvidenceJpaRepository
        .findByEdgeId(edgeId);

    List<EvidenceDto> evidenceDtos = evidenceEntities.stream()
        .map(evidence -> new EvidenceDto(
            evidence.getEvidenceId(),
            evidence.getEvidenceType(),
            evidence.getTitle(),
            evidence.getContent(),
            evidence.getSourceRef(),
            evidence.getCreatedAt()))
        .collect(Collectors.toList());

    // 3. 응답 DTO 생성
    return new SelectEdgeResDto(
        edge.getEdgeId(),
        edge.getFromIngredientId(),
        edge.getToIngredientId(),
        edge.getRelationType(),
        edge.getScore(),
        edge.getConfidence(),
        edge.getReasonSummary(),
        evidenceDtos,
        edge.getCreatedAt(),
        edge.getUpdatedAt());
  }

  @Override
  @Transactional
  public UpdateEdgeResDto updateEdge(UpdateEdgeReqDto req) {
    // 1. Edge 조회
    IngredientEdgeEntity edge = ingredientEdgeJpaRepository.findById(req.getEdgeId())
        .orElseThrow(() -> new GustoException("EDGE011")); // 관계를 찾을 수 없습니다.

    // 2. 관계 타입 검증
    IngredientEdgeEntity.IngredientRelationType relationType = req.getRelationType();
    if (relationType == null) {
      throw new GustoException("EDGE003"); // 관계 타입은 필수입니다.
    }
    // Enum 값이 유효한지 확인 (PAIR_WELL, AVOID, NEUTRAL 중 하나인지)
    boolean isValidType = false;
    for (IngredientEdgeEntity.IngredientRelationType validType : IngredientEdgeEntity.IngredientRelationType.values()) {
      if (validType == relationType) {
        isValidType = true;
        break;
      }
    }
    if (!isValidType) {
      throw new GustoException("EDGE003"); // 유효하지 않은 관계 타입입니다.
    }

    // 3. 점수 검증
    Integer score;

    if (relationType == IngredientEdgeEntity.IngredientRelationType.NEUTRAL) {
      // NEUTRAL은 점수가 없어도 됨 (0으로 설정)
      score = req.getScore() != null ? req.getScore() : 0;
    } else {
      // PAIR_WELL 또는 AVOID는 점수 필수
      if (req.getScore() == null) {
        throw new GustoException("EDGE004"); // 관계 점수는 필수입니다.
      }

      score = req.getScore();
      if (score < 1 || score > 10) {
        throw new GustoException("EDGE005"); // 관계 점수는 1-10 사이여야 합니다.
      }
    }

    // 4. 엔티티 수정
    edge.setRelationType(relationType);
    edge.setScore(score);
    edge.setConfidence(req.getConfidence());
    edge.setReasonSummary(req.getReasonSummary());

    // 5. 저장
    IngredientEdgeEntity updatedEdge = ingredientEdgeJpaRepository.save(edge);

    // 4. 응답 DTO 생성
    return new UpdateEdgeResDto(
        updatedEdge.getEdgeId(),
        updatedEdge.getFromIngredientId(),
        updatedEdge.getToIngredientId(),
        updatedEdge.getRelationType(),
        updatedEdge.getScore(),
        updatedEdge.getConfidence(),
        updatedEdge.getReasonSummary(),
        updatedEdge.getUpdatedAt());
  }

  @Override
  @Transactional
  public DeleteEdgeResDto deleteEdge(Long edgeId) {
    // 1. Edge 조회
    IngredientEdgeEntity edge = ingredientEdgeJpaRepository.findById(edgeId)
        .orElseThrow(() -> new GustoException("EDGE011")); // 관계를 찾을 수 없습니다.

    // 2. 관련 증거 삭제 (CASCADE 또는 수동 삭제)
    List<IngredientEdgeEvidenceEntity> evidenceList = ingredientEdgeEvidenceJpaRepository
        .findByEdgeId(edgeId);
    if (!evidenceList.isEmpty()) {
      ingredientEdgeEvidenceJpaRepository.deleteAll(evidenceList);
    }

    // 3. Edge 삭제
    ingredientEdgeJpaRepository.delete(edge);

    // 4. 응답 DTO 생성
    return new DeleteEdgeResDto(
        edgeId,
        "재료 간 관계가 성공적으로 삭제되었습니다.");
  }
}
