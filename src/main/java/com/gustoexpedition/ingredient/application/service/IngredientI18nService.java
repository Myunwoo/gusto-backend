package com.gustoexpedition.ingredient.application.service;

import com.gustoexpedition.common.exception.GustoException;
import com.gustoexpedition.ingredient.adapter.in.dto.*;
import com.gustoexpedition.ingredient.adapter.out.persistence.IngredientAliasJpaRepository;
import com.gustoexpedition.ingredient.adapter.out.persistence.IngredientI18nJpaRepository;
import com.gustoexpedition.ingredient.adapter.out.persistence.IngredientJpaRepository;
import com.gustoexpedition.ingredient.application.port.in.IngredientI18nUseCase;
import com.gustoexpedition.ingredient.domain.IngredientValidator;
import com.gustoexpedition.ingredient.entity.IngredientAliasEntity;
import com.gustoexpedition.ingredient.entity.IngredientI18nEntity;
import com.gustoexpedition.ingredient.entity.IngredientI18nId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * packageName : com.gustoexpedition.ingredient.application.service
 * fileName : IngredientI18nService
 * author : fddsg
 * date : 2026-01-16
 * description : 재료 locale별 정보 관리 서비스
 */
@Service
@RequiredArgsConstructor
public class IngredientI18nService implements IngredientI18nUseCase {

  private final IngredientJpaRepository ingredientJpaRepository;
  private final IngredientI18nJpaRepository ingredientI18nJpaRepository;
  private final IngredientAliasJpaRepository ingredientAliasJpaRepository;

  @Override
  @Transactional
  public CreateIngredientI18nResDto createIngredientI18n(CreateIngredientI18nReqDto req) {
    // 1. 재료 존재 확인
    ingredientJpaRepository.findById(req.getIngredientId())
        .orElseThrow(() -> new GustoException("INGR006")); // 재료를 찾을 수 없습니다.

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
        req.getDescription());
    IngredientI18nEntity savedI18n = ingredientI18nJpaRepository.save(i18nEntity);

    // 6. 응답 DTO 생성
    return new CreateIngredientI18nResDto(
        savedI18n.getIngredientId(),
        savedI18n.getLocale(),
        savedI18n.getName(),
        savedI18n.getDescription(),
        savedI18n.getCreatedAt(),
        savedI18n.getUpdatedAt());
  }

  @Override
  @Transactional
  public UpdateIngredientI18nResDto updateIngredientI18n(UpdateIngredientI18nReqDto req) {
    // 1. 재료 존재 확인
    ingredientJpaRepository.findById(req.getIngredientId())
        .orElseThrow(() -> new GustoException("INGR006")); // 재료를 찾을 수 없습니다.

    // 2. 입력 검증
    IngredientValidator.validateName(req.getName());

    // 3. i18n 정보 조회
    IngredientI18nEntity i18nEntity = ingredientI18nJpaRepository.findById(
        new IngredientI18nId(req.getIngredientId(), req.getLocale())).orElseThrow(() -> new GustoException("INGR007")); // 재료의
                                                                                                                        // locale
                                                                                                                        // 정보를
                                                                                                                        // 찾을
                                                                                                                        // 수
                                                                                                                        // 없습니다.

    // 4. 중복 체크 (다른 재료의 같은 locale과 name 조합인지 확인)
    ingredientI18nJpaRepository.findByLocaleAndName(req.getLocale(), req.getName().trim())
        .ifPresent(existing -> {
          // 자기 자신이 아닌 경우에만 중복 에러
          if (!existing.getIngredientId().equals(req.getIngredientId()) ||
              !existing.getLocale().equals(req.getLocale())) {
            throw new GustoException("INGR002"); // 이미 존재하는 재료명입니다.
          }
        });

    // 5. 엔티티 수정
    i18nEntity.setName(req.getName().trim());
    i18nEntity.setDescription(req.getDescription());

    // 6. 저장
    IngredientI18nEntity updatedI18n = ingredientI18nJpaRepository.save(i18nEntity);

    // 7. 응답 DTO 생성
    return new UpdateIngredientI18nResDto(
        updatedI18n.getIngredientId(),
        updatedI18n.getLocale(),
        updatedI18n.getName(),
        updatedI18n.getDescription(),
        updatedI18n.getUpdatedAt());
  }

  @Override
  @Transactional
  public DeleteIngredientI18nResDto deleteIngredientI18n(Long ingredientId, String locale) {
    // 1. 재료 존재 확인
    ingredientJpaRepository.findById(ingredientId)
        .orElseThrow(() -> new GustoException("INGR006")); // 재료를 찾을 수 없습니다.

    // 2. i18n 정보 조회
    IngredientI18nEntity i18nEntity = ingredientI18nJpaRepository.findById(
        new IngredientI18nId(ingredientId, locale)).orElseThrow(() -> new GustoException("INGR007")); // 재료의 locale 정보를
                                                                                                      // 찾을 수 없습니다.

    // 3. 관련 별칭도 함께 삭제 (해당 locale의 별칭)
    List<IngredientAliasEntity> aliasList = ingredientAliasJpaRepository
        .findByIngredientIdAndLocale(ingredientId, locale);
    if (!aliasList.isEmpty()) {
      ingredientAliasJpaRepository.deleteAll(aliasList);
    }

    // 4. i18n 정보 삭제
    ingredientI18nJpaRepository.delete(i18nEntity);

    // 5. 응답 DTO 생성
    return new DeleteIngredientI18nResDto(
        ingredientId,
        locale,
        "재료의 locale별 정보가 성공적으로 삭제되었습니다.");
  }
}
