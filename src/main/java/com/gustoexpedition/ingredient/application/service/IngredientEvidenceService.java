package com.gustoexpedition.ingredient.application.service;

import com.gustoexpedition.common.exception.GustoException;
import com.gustoexpedition.ingredient.adapter.in.dto.*;
import com.gustoexpedition.ingredient.adapter.out.persistence.IngredientEdgeEvidenceJpaRepository;
import com.gustoexpedition.ingredient.adapter.out.persistence.IngredientEdgeJpaRepository;
import com.gustoexpedition.ingredient.application.port.in.IngredientEvidenceUseCase;
import com.gustoexpedition.ingredient.entity.IngredientEdgeEvidenceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName : com.gustoexpedition.ingredient.application.service
 * fileName : IngredientEvidenceService
 * author : fddsg
 * date : 2026-01-16
 * description : 재료 간 관계 증거 관리 서비스
 */
@Service
@RequiredArgsConstructor
public class IngredientEvidenceService implements IngredientEvidenceUseCase {

    private final IngredientEdgeJpaRepository ingredientEdgeJpaRepository;
    private final IngredientEdgeEvidenceJpaRepository ingredientEdgeEvidenceJpaRepository;

    @Override
    @Transactional
    public CreateEvidenceResDto createEvidence(CreateEvidenceReqDto req) {
        // 1. Edge 존재 확인
        ingredientEdgeJpaRepository.findById(req.getEdgeId())
                .orElseThrow(() -> new GustoException("EDGE011")); // 관계를 찾을 수 없습니다.

        // 2. 증거 타입 검증
        IngredientEdgeEvidenceEntity.EdgeEvidenceType evidenceType = req.getEvidenceType();
        if (evidenceType == null) {
            throw new GustoException("EVIDENCE002"); // 증거 타입은 필수입니다.
        }
        // Enum 값이 유효한지 확인
        boolean isValidType = false;
        for (IngredientEdgeEvidenceEntity.EdgeEvidenceType validType : IngredientEdgeEvidenceEntity.EdgeEvidenceType
                .values()) {
            if (validType == evidenceType) {
                isValidType = true;
                break;
            }
        }
        if (!isValidType) {
            throw new GustoException("EVIDENCE002"); // 유효하지 않은 증거 타입입니다.
        }

        // 3. Evidence 엔티티 생성 및 저장
        IngredientEdgeEvidenceEntity evidenceEntity = new IngredientEdgeEvidenceEntity(
                req.getEdgeId(),
                req.getEvidenceType(),
                req.getTitle(),
                req.getContent(),
                req.getSourceRef());
        IngredientEdgeEvidenceEntity savedEvidence = ingredientEdgeEvidenceJpaRepository.save(evidenceEntity);

        // 3. 응답 DTO 생성
        return new CreateEvidenceResDto(
                savedEvidence.getEvidenceId(),
                savedEvidence.getEdgeId(),
                savedEvidence.getEvidenceType(),
                savedEvidence.getTitle(),
                savedEvidence.getContent(),
                savedEvidence.getSourceRef(),
                savedEvidence.getCreatedAt());
    }

    @Override
    @Transactional
    public UpdateEvidenceResDto updateEvidence(UpdateEvidenceReqDto req) {
        // 1. Evidence 조회
        IngredientEdgeEvidenceEntity evidence = ingredientEdgeEvidenceJpaRepository
                .findById(req.getEvidenceId())
                .orElseThrow(() -> new GustoException("EVIDENCE005")); // 증거를 찾을 수 없습니다.

        // 2. 증거 타입 검증
        IngredientEdgeEvidenceEntity.EdgeEvidenceType evidenceType = req.getEvidenceType();
        if (evidenceType == null) {
            throw new GustoException("EVIDENCE002"); // 증거 타입은 필수입니다.
        }
        // Enum 값이 유효한지 확인
        boolean isValidType = false;
        for (IngredientEdgeEvidenceEntity.EdgeEvidenceType validType : IngredientEdgeEvidenceEntity.EdgeEvidenceType
                .values()) {
            if (validType == evidenceType) {
                isValidType = true;
                break;
            }
        }
        if (!isValidType) {
            throw new GustoException("EVIDENCE002"); // 유효하지 않은 증거 타입입니다.
        }

        // 3. 엔티티 수정
        evidence.setEvidenceType(evidenceType);
        evidence.setTitle(req.getTitle());
        evidence.setContent(req.getContent());
        evidence.setSourceRef(req.getSourceRef());

        // 3. 저장
        IngredientEdgeEvidenceEntity updatedEvidence = ingredientEdgeEvidenceJpaRepository.save(evidence);

        // 4. 응답 DTO 생성
        return new UpdateEvidenceResDto(
                updatedEvidence.getEvidenceId(),
                updatedEvidence.getEdgeId(),
                updatedEvidence.getEvidenceType(),
                updatedEvidence.getTitle(),
                updatedEvidence.getContent(),
                updatedEvidence.getSourceRef(),
                updatedEvidence.getCreatedAt());
    }

    @Override
    @Transactional
    public DeleteEvidenceResDto deleteEvidence(Long evidenceId) {
        // 1. Evidence 조회
        IngredientEdgeEvidenceEntity evidence = ingredientEdgeEvidenceJpaRepository.findById(evidenceId)
                .orElseThrow(() -> new GustoException("EVIDENCE005")); // 증거를 찾을 수 없습니다.

        // 2. Evidence 삭제
        ingredientEdgeEvidenceJpaRepository.delete(evidence);

        // 3. 응답 DTO 생성
        return new DeleteEvidenceResDto(
                evidenceId,
                "증거가 성공적으로 삭제되었습니다.");
    }
}
