package com.gustoexpedition.ingredient.application.service;

import com.gustoexpedition.ingredient.adapter.in.dto.CreateIngredientReqDto;
import com.gustoexpedition.ingredient.adapter.in.dto.CreateIngredientResDto;
import com.gustoexpedition.ingredient.adapter.out.persistence.IngredientAliasJpaRepository;
import com.gustoexpedition.ingredient.adapter.out.persistence.IngredientI18nJpaRepository;
import com.gustoexpedition.ingredient.adapter.out.persistence.IngredientJpaRepository;
import com.gustoexpedition.ingredient.application.port.in.IngredientAdminUseCase;
import com.gustoexpedition.ingredient.domain.IngredientValidator;
import com.gustoexpedition.ingredient.entity.IngredientAliasEntity;
import com.gustoexpedition.ingredient.entity.IngredientEntity;
import com.gustoexpedition.ingredient.entity.IngredientI18nEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * packageName    : com.gustoexpedition.ingredient.application.service
 * fileName       : IngredientAdminService
 * author         : fddsg
 * date           : 2026-01-14
 * description    : 재료 관리 서비스 (V2 스키마 반영)
 */
@Service
@RequiredArgsConstructor
public class IngredientAdminService implements IngredientAdminUseCase {

    private final IngredientJpaRepository ingredientJpaRepository;
    private final IngredientI18nJpaRepository ingredientI18nJpaRepository;
    private final IngredientAliasJpaRepository ingredientAliasJpaRepository;

    @Override
    @Transactional
    public CreateIngredientResDto createIngredient(CreateIngredientReqDto req) {
        // 1. 입력 검증
        IngredientValidator.validateName(req.getName());
        String locale = req.getLocale() != null ? req.getLocale() : "ko-KR";

        // 2. 중복 체크 (같은 locale과 name 조합)
        ingredientI18nJpaRepository.findByLocaleAndName(locale, req.getName().trim())
                .ifPresent(existing -> {
                    throw new IllegalArgumentException(
                            String.format("이미 존재하는 재료입니다: locale=%s, name=%s", locale, req.getName())
                    );
                });

        // 3. Ingredient 엔티티 생성 및 저장
        IngredientEntity ingredientEntity = new IngredientEntity(
                req.getThumbnailUrl(),
                req.getIsActive()
        );
        IngredientEntity savedIngredient = ingredientJpaRepository.save(ingredientEntity);

        // 4. IngredientI18n 엔티티 생성 및 저장
        IngredientI18nEntity i18nEntity = new IngredientI18nEntity(
                savedIngredient.getIngredientId(),
                locale,
                req.getName().trim(),
                req.getDescription()
        );
        IngredientI18nEntity savedI18n = ingredientI18nJpaRepository.save(i18nEntity);

        // 5. 별칭 저장 (있는 경우)
        if (req.getAliases() != null && !req.getAliases().isEmpty()) {
            List<IngredientAliasEntity> aliasEntities = req.getAliases().stream()
                    .filter(alias -> alias != null && !alias.trim().isEmpty())
                    .map(alias -> new IngredientAliasEntity(
                            savedIngredient.getIngredientId(),
                            locale,
                            alias.trim()
                    ))
                    .collect(Collectors.toList());
            ingredientAliasJpaRepository.saveAll(aliasEntities);
        }

        // 6. 응답 DTO 생성
        return new CreateIngredientResDto(
                savedIngredient.getIngredientId(),
                savedI18n.getName(),
                savedI18n.getLocale(),
                savedI18n.getDescription(),
                savedIngredient.getThumbnailUrl(),
                savedIngredient.getIsActive(),
                savedIngredient.getCreatedAt()
        );
    }
}
