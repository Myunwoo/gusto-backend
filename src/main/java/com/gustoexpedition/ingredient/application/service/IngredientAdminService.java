package com.gustoexpedition.ingredient.application.service;

import com.gustoexpedition.common.exception.GustoException;
import com.gustoexpedition.ingredient.adapter.in.dto.*;
import com.gustoexpedition.ingredient.adapter.out.persistence.IngredientAliasJpaRepository;
import com.gustoexpedition.ingredient.adapter.out.persistence.IngredientEdgeJpaRepository;
import com.gustoexpedition.ingredient.adapter.out.persistence.IngredientI18nJpaRepository;
import com.gustoexpedition.ingredient.adapter.out.persistence.IngredientJpaRepository;
import com.gustoexpedition.ingredient.application.port.in.IngredientAdminUseCase;
import com.gustoexpedition.ingredient.domain.IngredientValidator;
import com.gustoexpedition.ingredient.entity.IngredientAliasEntity;
import com.gustoexpedition.ingredient.entity.IngredientEdgeEntity;
import com.gustoexpedition.ingredient.entity.IngredientEntity;
import com.gustoexpedition.ingredient.entity.IngredientI18nEntity;
import com.gustoexpedition.ingredient.entity.IngredientI18nId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * packageName    : com.gustoexpedition.ingredient.application.service
 * fileName       : IngredientAdminService
 * author         : fddsg
 * date           : 2026-01-14
 * description    : 재료 관리 서비스
 */
@Service
@RequiredArgsConstructor
public class IngredientAdminService implements IngredientAdminUseCase {

    private final IngredientJpaRepository ingredientJpaRepository;
    private final IngredientI18nJpaRepository ingredientI18nJpaRepository;
    private final IngredientAliasJpaRepository ingredientAliasJpaRepository;
    private final IngredientEdgeJpaRepository ingredientEdgeJpaRepository;

    @Override
    @Transactional
    public CreateIngredientBasicResDto createIngredient(CreateIngredientBasicReqDto req) {
        // 1. 입력 검증
        IngredientValidator.validateName(req.getName());

        // 2. Ingredient 엔티티 생성 및 저장 (기본정보만)
        IngredientEntity ingredientEntity = new IngredientEntity(
                req.getName().trim(),
                req.getThumbnailUrl(),
                req.getIsActive()
        );
        IngredientEntity savedIngredient = ingredientJpaRepository.save(ingredientEntity);

        // 3. 응답 DTO 생성
        return new CreateIngredientBasicResDto(
                savedIngredient.getIngredientId(),
                savedIngredient.getName(),
                savedIngredient.getThumbnailUrl(),
                savedIngredient.getIsActive(),
                savedIngredient.getCreatedAt()
        );
    }

    @Override
    @Transactional
    public CreateIngredientI18nResDto createIngredientI18n(CreateIngredientI18nReqDto req) {
        // 1. 재료 존재 확인
        IngredientEntity ingredient = ingredientJpaRepository.findById(req.getIngredientId())
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("재료를 찾을 수 없습니다: ingredientId=%d", req.getIngredientId())
                ));

        // 2. 입력 검증
        IngredientValidator.validateName(req.getName());

        // 3. 중복 체크 (같은 locale과 name 조합)
        ingredientI18nJpaRepository.findByLocaleAndName(req.getLocale(), req.getName().trim())
                .ifPresent(existing -> {
                    throw new GustoException("INGR002");
                });

        // 4. 같은 재료의 같은 locale이 이미 존재하는지 확인
        ingredientI18nJpaRepository.findById(new IngredientI18nId(req.getIngredientId(), req.getLocale()))
                .ifPresent(existing -> {
                    throw new GustoException("INGR003");
                });

        // 5. IngredientI18n 엔티티 생성 및 저장
        IngredientI18nEntity i18nEntity = new IngredientI18nEntity(
                req.getIngredientId(),
                req.getLocale(),
                req.getName().trim(),
                req.getDescription()
        );
        IngredientI18nEntity savedI18n = ingredientI18nJpaRepository.save(i18nEntity);

        // 6. 응답 DTO 생성
        return new CreateIngredientI18nResDto(
                savedI18n.getIngredientId(),
                savedI18n.getLocale(),
                savedI18n.getName(),
                savedI18n.getDescription(),
                savedI18n.getCreatedAt(),
                savedI18n.getUpdatedAt()
        );
    }

    @Override
    @Transactional
    public CreateAliasResDto createAlias(CreateAliasReqDto req) {
        // 1. 재료 존재 확인
        IngredientEntity ingredient = ingredientJpaRepository.findById(req.getIngredientId())
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("재료를 찾을 수 없습니다: ingredientId=%d", req.getIngredientId())
                ));

        // 2. 재료의 해당 locale 정보 존재 확인
        ingredientI18nJpaRepository.findById(new IngredientI18nId(req.getIngredientId(), req.getLocale()))
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("재료의 %s 언어 정보를 찾을 수 없습니다: ingredientId=%d", req.getLocale(), req.getIngredientId())
                ));

        // 3. 별칭 엔티티 생성 및 저장
        List<IngredientAliasEntity> aliasEntities = req.getAliases().stream()
                .filter(alias -> alias != null && !alias.trim().isEmpty())
                .map(alias -> new IngredientAliasEntity(
                        req.getIngredientId(),
                        req.getLocale(),
                        alias.trim()
                ))
                .collect(Collectors.toList());

        if (aliasEntities.isEmpty()) {
            throw new IllegalArgumentException("유효한 별칭이 없습니다.");
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
                savedAliases.get(0).getCreatedAt()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public SelectIngredientResDto selectById(Long ingredientId, String locale, Boolean includeRelationYn) {
        // 1. 기본 정보 조회
        IngredientEntity ingredient = ingredientJpaRepository.findById(ingredientId)
                .orElse(null);
        
        // 조회된 정보 없을 경우 null 반환
        if (ingredient == null) {
            return null;
        }

        // 2. i18n 정보 조회 (locale이 있으면 해당 locale만, 없으면 모든 locale)
        Map<String, IngredientLocaleInfoDto> localeInfoMap = new HashMap<>();
        List<IngredientI18nEntity> i18nList;
        
        if (locale != null) {
            // 특정 locale만 조회 (없으면 빈 Map 반환)
            Optional<IngredientI18nEntity> i18nOpt = ingredientI18nJpaRepository.findById(
                    new IngredientI18nId(ingredientId, locale)
            );
            if (i18nOpt.isPresent()) {
                IngredientI18nEntity i18n = i18nOpt.get();
                localeInfoMap.put(i18n.getLocale(), new IngredientLocaleInfoDto(
                        i18n.getName(),
                        i18n.getDescription()
                ));
                i18nList = List.of(i18n);
            } else {
                i18nList = new ArrayList<>();
            }
        } else {
            // 모든 locale 조회
            i18nList = ingredientI18nJpaRepository.findByIngredientId(ingredientId);
            // locale별 정보를 Map으로 변환
            for (IngredientI18nEntity i18n : i18nList) {
                localeInfoMap.put(i18n.getLocale(), new IngredientLocaleInfoDto(
                        i18n.getName(),
                        i18n.getDescription()
                ));
            }
        }

        // 3. 별칭 목록 조회 (locale별로 그룹화)
        Map<String, List<String>> aliasesMap = new HashMap<>();
        List<IngredientAliasEntity> aliasEntities;
        
        if (locale != null) {
            // 특정 locale만 조회
            aliasEntities = ingredientAliasJpaRepository.findByIngredientIdAndLocale(ingredientId, locale);
        } else {
            // 모든 locale 조회
            aliasEntities = ingredientAliasJpaRepository.findByIngredientId(ingredientId);
        }
        
        // locale별로 그룹화
        aliasesMap = aliasEntities.stream()
                .collect(Collectors.groupingBy(
                        IngredientAliasEntity::getLocale,
                        Collectors.mapping(
                                IngredientAliasEntity::getAlias,
                                Collectors.toList()
                        )
                ));

        // 4. 관계 정보 조회 (includeRelationYn이 true인 경우)
        List<RelatedIngredientDto> relatedIngredients = new ArrayList<>();
        if (Boolean.TRUE.equals(includeRelationYn) && !i18nList.isEmpty()) {
            List<IngredientEdgeEntity> edges = ingredientEdgeJpaRepository
                    .findByFromIngredientIdOrToIngredientId(ingredientId, ingredientId);

            // locale이 있으면 해당 locale만, 없으면 첫 번째 locale 사용
            String targetLocale = locale != null ? locale : 
                    (i18nList.isEmpty() ? null : i18nList.get(0).getLocale());

            if (targetLocale != null) {
                for (IngredientEdgeEntity edge : edges) {
                    // 현재 재료와 관련된 다른 재료 ID 찾기
                    Long relatedIngredientId = edge.getFromIngredientId().equals(ingredientId)
                            ? edge.getToIngredientId()
                            : edge.getFromIngredientId();

                    // 관련 재료의 i18n 정보 조회
                    ingredientI18nJpaRepository.findById(
                            new IngredientI18nId(relatedIngredientId, targetLocale)
                    ).ifPresent(relatedI18n -> {
                        relatedIngredients.add(new RelatedIngredientDto(
                                relatedIngredientId,
                                relatedI18n.getName(),
                                edge.getRelationType().name(),
                                edge.getScore(),
                                edge.getConfidence(),
                                edge.getReasonSummary()
                        ));
                    });
                }
            }
        }

        // 5. 응답 DTO 생성
        return new SelectIngredientResDto(
                ingredient.getIngredientId(),
                localeInfoMap,
                ingredient.getThumbnailUrl(),
                ingredient.getIsActive(),
                aliasesMap,
                relatedIngredients,
                ingredient.getCreatedAt(),
                ingredient.getUpdatedAt()
        );
    }
}
