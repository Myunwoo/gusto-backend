package com.gustoexpedition.ingredient.application.service;

import com.gustoexpedition.common.exception.GustoException;
import com.gustoexpedition.ingredient.adapter.in.dto.*;
import com.gustoexpedition.ingredient.adapter.out.persistence.IngredientAliasJpaRepository;
import com.gustoexpedition.ingredient.adapter.out.persistence.IngredientI18nJpaRepository;
import com.gustoexpedition.ingredient.adapter.out.persistence.IngredientJpaRepository;
import com.gustoexpedition.ingredient.application.port.in.IngredientAliasUseCase;
import com.gustoexpedition.ingredient.entity.IngredientAliasEntity;
import com.gustoexpedition.ingredient.entity.IngredientI18nId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * packageName : com.gustoexpedition.ingredient.application.service
 * fileName : IngredientAliasService
 * author : fddsg
 * date : 2026-01-16
 * description : 재료 별칭 관리 서비스
 */
@Service
@RequiredArgsConstructor
public class IngredientAliasService implements IngredientAliasUseCase {

    private final IngredientJpaRepository ingredientJpaRepository;
    private final IngredientI18nJpaRepository ingredientI18nJpaRepository;
    private final IngredientAliasJpaRepository ingredientAliasJpaRepository;

    @Override
    @Transactional
    public CreateAliasResDto createAlias(CreateAliasReqDto req) {
        // 1. 재료 존재 확인
        ingredientJpaRepository.findById(req.getIngredientId())
                .orElseThrow(() -> new GustoException("INGR006")); // 재료를 찾을 수 없습니다.

        // 2. 재료의 locale 정보 존재 확인
        ingredientI18nJpaRepository.findById(new IngredientI18nId(req.getIngredientId(), req.getLocale()))
                .orElseThrow(() -> new GustoException("INGR007")); // 재료의 locale 정보를 찾을 수 없습니다.

        // 3. 별칭 엔티티 생성 및 저장
        List<IngredientAliasEntity> aliasEntities = req.getAliases().stream()
                .filter(alias -> alias != null && !alias.trim().isEmpty())
                .map(alias -> new IngredientAliasEntity(
                        req.getIngredientId(),
                        req.getLocale(),
                        alias.trim()))
                .collect(Collectors.toList());

        if (aliasEntities.isEmpty()) {
            throw new GustoException("INGR008"); // 유효한 별칭이 없습니다.
        }

        List<IngredientAliasEntity> savedAliases = ingredientAliasJpaRepository.saveAll(aliasEntities);

        // 4. 응답 DTO 생성
        List<String> aliasList = savedAliases.stream()
                .map(IngredientAliasEntity::getAlias)
                .collect(Collectors.toList());

        return new CreateAliasResDto(
                req.getIngredientId(),
                req.getLocale(),
                aliasList,
                savedAliases.get(0).getCreatedAt());
    }

    @Override
    @Transactional
    public UpdateAliasAllResDto updateAliasAll(UpdateAliasAllReqDto req) {
        // 재료 존재 확인
        ingredientJpaRepository.findById(req.getIngredientId())
                .orElseThrow(() -> new GustoException("INGR006")); // 재료를 찾을 수 없습니다.

        // 재료의 locale 정보 존재 확인
        ingredientI18nJpaRepository.findById(new IngredientI18nId(req.getIngredientId(), req.getLocale()))
                .orElseThrow(() -> new GustoException("INGR007")); // 재료의 locale 정보를 찾을 수 없습니다.

        // 기존 별칭 삭제
        List<IngredientAliasEntity> existingAliases = ingredientAliasJpaRepository
                .findByIngredientIdAndLocale(req.getIngredientId(), req.getLocale());
        if (!existingAliases.isEmpty()) {
            ingredientAliasJpaRepository.deleteAll(existingAliases);
        }

        // 새 별칭 생성 및 저장
        List<IngredientAliasEntity> aliasEntities = req.getAliases().stream()
                .filter(alias -> alias != null && !alias.trim().isEmpty())
                .map(alias -> new IngredientAliasEntity(
                        req.getIngredientId(),
                        req.getLocale(),
                        alias.trim()))
                .collect(Collectors.toList());

        // 별칭이 없으면 빈 리스트 반환 (별칭은 선택사항)
        if (aliasEntities.isEmpty()) {
            return new UpdateAliasAllResDto(
                    req.getIngredientId(),
                    req.getLocale(),
                    List.of(),
                    existingAliases.isEmpty()
                            ? java.time.Instant.now()
                            : existingAliases.get(0).getCreatedAt());
        }

        List<IngredientAliasEntity> savedAliases = ingredientAliasJpaRepository.saveAll(aliasEntities);

        // 응답 DTO 생성
        List<String> aliasList = savedAliases.stream()
                .map(IngredientAliasEntity::getAlias)
                .collect(Collectors.toList());

        return new UpdateAliasAllResDto(
                req.getIngredientId(),
                req.getLocale(),
                aliasList,
                savedAliases.get(0).getCreatedAt());
    }

    @Override
    @Transactional
    public UpdateAliasResDto updateAlias(UpdateAliasReqDto req) {
        // 1. 별칭 조회
        IngredientAliasEntity aliasEntity = ingredientAliasJpaRepository.findById(req.getAliasId())
                .orElseThrow(() -> new GustoException("INGR012")); // 별칭을 찾을 수 없습니다.

        // 2. 입력 검증
        if (req.getAlias() == null || req.getAlias().trim().isEmpty()) {
            throw new GustoException("INGR010"); // 별칭은 필수입니다.
        }

        // 3. 중복 체크 (같은 재료, 같은 locale에서 같은 별칭이 이미 존재하는지 확인)
        ingredientAliasJpaRepository.findByIngredientIdAndLocale(aliasEntity.getIngredientId(), aliasEntity.getLocale())
                .stream()
                .filter(existing -> !existing.getAliasId().equals(req.getAliasId()))
                .filter(existing -> existing.getAlias().equals(req.getAlias().trim()))
                .findFirst()
                .ifPresent(existing -> {
                    throw new GustoException("INGR002"); // 이미 존재하는 재료명입니다.
                });

        // 4. 별칭 수정
        aliasEntity.setAlias(req.getAlias().trim());
        IngredientAliasEntity savedAlias = ingredientAliasJpaRepository.save(aliasEntity);

        // 5. 응답 DTO 생성
        return new UpdateAliasResDto(
                savedAlias.getAliasId(),
                savedAlias.getIngredientId(),
                savedAlias.getLocale(),
                savedAlias.getAlias(),
                savedAlias.getCreatedAt());
    }

    @Override
    @Transactional
    public DeleteAliasAllResDto deleteAliasAll(Long ingredientId, String locale) {
        // 1. 재료 존재 확인
        ingredientJpaRepository.findById(ingredientId)
                .orElseThrow(() -> new GustoException("INGR006")); // 재료를 찾을 수 없습니다.

        // 2. 재료의 locale 정보 존재 확인
        ingredientI18nJpaRepository.findById(new IngredientI18nId(ingredientId, locale))
                .orElseThrow(() -> new GustoException("INGR007")); // 재료의 locale 정보를 찾을 수 없습니다.

        // 3. 별칭 조회
        List<IngredientAliasEntity> aliasList = ingredientAliasJpaRepository
                .findByIngredientIdAndLocale(ingredientId, locale);

        if (aliasList.isEmpty()) {
            throw new GustoException("INGR009"); // 삭제할 별칭이 없습니다.
        }

        // 4. 별칭 삭제
        ingredientAliasJpaRepository.deleteAll(aliasList);

        // 5. 응답 DTO 생성
        return new DeleteAliasAllResDto(
                ingredientId,
                locale,
                "재료의 별칭이 성공적으로 삭제되었습니다.");
    }

    @Override
    @Transactional
    public DeleteAliasResDto deleteAlias(Long aliasId) {
        // 1. 별칭 조회
        IngredientAliasEntity aliasEntity = ingredientAliasJpaRepository.findById(aliasId)
                .orElseThrow(() -> new GustoException("INGR012")); // 별칭을 찾을 수 없습니다.

        // 2. 별칭 정보 저장 (삭제 전)
        Long ingredientId = aliasEntity.getIngredientId();
        String locale = aliasEntity.getLocale();
        String alias = aliasEntity.getAlias();

        // 3. 별칭 삭제
        ingredientAliasJpaRepository.delete(aliasEntity);

        // 4. 응답 DTO 생성
        return new DeleteAliasResDto(
                aliasId,
                ingredientId,
                locale,
                alias,
                "별칭이 성공적으로 삭제되었습니다.");
    }
}
