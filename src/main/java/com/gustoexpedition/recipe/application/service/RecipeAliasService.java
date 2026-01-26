package com.gustoexpedition.recipe.application.service;

import com.gustoexpedition.common.exception.GustoException;
import com.gustoexpedition.recipe.adapter.in.dto.*;
import com.gustoexpedition.recipe.adapter.out.persistence.RecipeAliasJpaRepository;
import com.gustoexpedition.recipe.adapter.out.persistence.RecipeI18nJpaRepository;
import com.gustoexpedition.recipe.adapter.out.persistence.RecipeJpaRepository;
import com.gustoexpedition.recipe.application.port.in.RecipeAliasUseCase;
import com.gustoexpedition.recipe.entity.RecipeAliasEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * packageName : com.gustoexpedition.recipe.application.service
 * fileName : RecipeAliasService
 * author : fddsg
 * date : 2026-01-20
 * description : 레시피 별칭 관리 서비스
 */
@Service
@RequiredArgsConstructor
public class RecipeAliasService implements RecipeAliasUseCase {

        private final RecipeJpaRepository recipeJpaRepository;
        private final RecipeI18nJpaRepository recipeI18nJpaRepository;
        private final RecipeAliasJpaRepository recipeAliasJpaRepository;

        @Override
        @Transactional
        public CreateAliasResDto createAlias(CreateAliasReqDto req) {
                // 1. 레시피 존재 확인
                recipeJpaRepository.findById(req.getRecipeId())
                                .orElseThrow(() -> new GustoException("RECIPE002")); // 레시피를 찾을 수 없습니다.

                // 2. 레시피의 locale 정보 존재 확인
                recipeI18nJpaRepository.findByRecipeIdAndLocale(req.getRecipeId(), req.getLocale())
                                .orElseThrow(() -> new GustoException("RECIPE003")); // 레시피의 locale 정보를 찾을 수 없습니다.

                // 3. 별칭 엔티티 생성 및 저장 (중복 제거)
                List<RecipeAliasEntity> aliasEntities = req.getAliases().stream()
                                .filter(alias -> alias != null && !alias.trim().isEmpty())
                                .map(alias -> alias.trim())
                                .distinct() // 중복 제거
                                .map(alias -> new RecipeAliasEntity(
                                                req.getRecipeId(),
                                                req.getLocale(),
                                                alias))
                                .collect(Collectors.toList());

                if (aliasEntities.isEmpty()) {
                        throw new GustoException("ALIAS005"); // 유효한 별칭이 없습니다.
                }

                List<RecipeAliasEntity> savedAliases = recipeAliasJpaRepository.saveAll(aliasEntities);

                // 4. 응답 DTO 생성
                List<String> aliasList = savedAliases.stream()
                                .map(RecipeAliasEntity::getAlias)
                                .collect(Collectors.toList());

                return new CreateAliasResDto(
                                req.getRecipeId(),
                                req.getLocale(),
                                aliasList,
                                savedAliases.get(0).getCreatedAt());
        }

        @Override
        @Transactional
        public UpdateAliasAllResDto updateAliasAll(UpdateAliasAllReqDto req) {
                // 레시피 존재 확인
                recipeJpaRepository.findById(req.getRecipeId())
                                .orElseThrow(() -> new GustoException("RECIPE002")); // 레시피를 찾을 수 없습니다.

                // 레시피의 locale 정보 존재 확인
                recipeI18nJpaRepository.findByRecipeIdAndLocale(req.getRecipeId(), req.getLocale())
                                .orElseThrow(() -> new GustoException("RECIPE003")); // 레시피의 locale 정보를 찾을 수 없습니다.

                // 기존 별칭 조회
                List<RecipeAliasEntity> existingAliases = recipeAliasJpaRepository
                                .findByRecipeIdAndLocale(req.getRecipeId(), req.getLocale());

                // 새 별칭 목록 정리 (중복 제거, 공백 제거)
                Set<String> newAliasSet = req.getAliases().stream()
                                .filter(alias -> alias != null && !alias.trim().isEmpty())
                                .map(String::trim)
                                .collect(Collectors.toSet());

                // 기존 별칭을 Map으로 변환 (별칭 문자열 -> 엔티티)
                Map<String, RecipeAliasEntity> existingAliasMap = existingAliases.stream()
                                .collect(Collectors.toMap(
                                                RecipeAliasEntity::getAlias,
                                                entity -> entity,
                                                (existing, replacement) -> existing));

                // 삭제할 별칭: 기존에는 있지만 새 목록에는 없는 것
                List<RecipeAliasEntity> toDelete = existingAliases.stream()
                                .filter(entity -> !newAliasSet.contains(entity.getAlias()))
                                .collect(Collectors.toList());

                // 추가할 별칭: 새 목록에는 있지만 기존에는 없는 것
                List<RecipeAliasEntity> toAdd = newAliasSet.stream()
                                .filter(alias -> !existingAliasMap.containsKey(alias))
                                .map(alias -> new RecipeAliasEntity(
                                                req.getRecipeId(),
                                                req.getLocale(),
                                                alias))
                                .collect(Collectors.toList());

                // 삭제 실행
                if (!toDelete.isEmpty()) {
                        recipeAliasJpaRepository.deleteAll(toDelete);
                }

                // 추가 실행
                if (!toAdd.isEmpty()) {
                        recipeAliasJpaRepository.saveAll(toAdd);
                }

                // 최종 별칭 목록 생성 (유지된 것 + 새로 추가된 것)
                List<String> finalAliasList = newAliasSet.stream()
                                .sorted()
                                .collect(Collectors.toList());

                // 생성 시간 결정: 기존 별칭이 있었으면 첫 번째 기존 별칭의 생성 시간 사용, 없었으면 현재 시간
                java.time.Instant createdAt = existingAliases.isEmpty()
                                ? java.time.Instant.now()
                                : existingAliases.get(0).getCreatedAt();

                return new UpdateAliasAllResDto(
                                req.getRecipeId(),
                                req.getLocale(),
                                finalAliasList,
                                createdAt);
        }

        @Override
        @Transactional
        public UpdateAliasResDto updateAlias(UpdateAliasReqDto req) {
                // 1. 별칭 조회
                RecipeAliasEntity aliasEntity = recipeAliasJpaRepository.findById(req.getAliasId())
                                .orElseThrow(() -> new GustoException("ALIAS006")); // 별칭을 찾을 수 없습니다.

                // 2. 입력 검증
                if (req.getAlias() == null || req.getAlias().trim().isEmpty()) {
                        throw new GustoException("ALIAS003"); // 별칭은 필수입니다.
                }

                // 3. 중복 체크 (같은 레시피, 같은 locale에서 같은 별칭이 이미 존재하는지 확인)
                recipeAliasJpaRepository
                                .findByRecipeIdAndLocale(aliasEntity.getRecipeId(), aliasEntity.getLocale())
                                .stream()
                                .filter(existing -> !existing.getAliasId().equals(req.getAliasId()))
                                .filter(existing -> existing.getAlias().equals(req.getAlias().trim()))
                                .findFirst()
                                .ifPresent(existing -> {
                                        throw new GustoException("ALIAS007"); // 이미 존재하는 별칭입니다.
                                });

                // 4. 별칭 수정
                aliasEntity.setAlias(req.getAlias().trim());
                RecipeAliasEntity savedAlias = recipeAliasJpaRepository.save(aliasEntity);

                // 5. 응답 DTO 생성
                return new UpdateAliasResDto(
                                savedAlias.getAliasId(),
                                savedAlias.getRecipeId(),
                                savedAlias.getLocale(),
                                savedAlias.getAlias(),
                                savedAlias.getCreatedAt());
        }

        @Override
        @Transactional
        public DeleteAliasAllResDto deleteAliasAll(Long recipeId, String locale) {
                // 1. 레시피 존재 확인
                recipeJpaRepository.findById(recipeId)
                                .orElseThrow(() -> new GustoException("RECIPE002")); // 레시피를 찾을 수 없습니다.

                // 2. 레시피의 locale 정보 존재 확인
                recipeI18nJpaRepository.findByRecipeIdAndLocale(recipeId, locale)
                                .orElseThrow(() -> new GustoException("RECIPE003")); // 레시피의 locale 정보를 찾을 수 없습니다.

                // 3. 별칭 조회
                List<RecipeAliasEntity> aliasList = recipeAliasJpaRepository
                                .findByRecipeIdAndLocale(recipeId, locale);

                if (aliasList.isEmpty()) {
                        throw new GustoException("ALIAS008"); // 삭제할 별칭이 없습니다.
                }

                // 4. 별칭 삭제
                recipeAliasJpaRepository.deleteAll(aliasList);

                // 5. 응답 DTO 생성
                return new DeleteAliasAllResDto(
                                recipeId,
                                locale,
                                "레시피의 별칭이 성공적으로 삭제되었습니다.");
        }

        @Override
        @Transactional
        public DeleteAliasResDto deleteAlias(Long aliasId) {
                // 1. 별칭 조회
                RecipeAliasEntity aliasEntity = recipeAliasJpaRepository.findById(aliasId)
                                .orElseThrow(() -> new GustoException("ALIAS006")); // 별칭을 찾을 수 없습니다.

                // 2. 별칭 정보 저장 (삭제 전)
                Long recipeId = aliasEntity.getRecipeId();
                String locale = aliasEntity.getLocale();
                String alias = aliasEntity.getAlias();

                // 3. 별칭 삭제
                recipeAliasJpaRepository.delete(aliasEntity);

                // 4. 응답 DTO 생성
                return new DeleteAliasResDto(
                                aliasId,
                                recipeId,
                                locale,
                                alias,
                                "별칭이 성공적으로 삭제되었습니다.");
        }
}

