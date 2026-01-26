package com.gustoexpedition.recipe.application.service;

import com.gustoexpedition.common.exception.GustoException;
import com.gustoexpedition.ingredient.adapter.out.persistence.IngredientJpaRepository;
import com.gustoexpedition.recipe.adapter.in.dto.*;
import com.gustoexpedition.recipe.adapter.out.persistence.RecipeAliasJpaRepository;
import com.gustoexpedition.recipe.adapter.out.persistence.RecipeI18nJpaRepository;
import com.gustoexpedition.recipe.adapter.out.persistence.RecipeIngredientJpaRepository;
import com.gustoexpedition.recipe.adapter.out.persistence.RecipeJpaRepository;
import com.gustoexpedition.recipe.application.port.in.RecipeAdminUseCase;
import com.gustoexpedition.recipe.entity.RecipeAliasEntity;
import com.gustoexpedition.recipe.entity.RecipeEntity;
import com.gustoexpedition.recipe.entity.RecipeI18nEntity;
import com.gustoexpedition.recipe.entity.RecipeIngredientEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * packageName : com.gustoexpedition.recipe.application.service
 * fileName : RecipeAdminService
 * author : fddsg
 * date : 2026-01-16
 * description : 레시피 관리 서비스
 */
@Service
@RequiredArgsConstructor
public class RecipeAdminService implements RecipeAdminUseCase {

  private final RecipeJpaRepository recipeJpaRepository;
  private final RecipeI18nJpaRepository recipeI18nJpaRepository;
  private final RecipeAliasJpaRepository recipeAliasJpaRepository;
  private final RecipeIngredientJpaRepository recipeIngredientJpaRepository;
  private final IngredientJpaRepository ingredientJpaRepository;

  @Override
  @Transactional
  public CreateRecipeResDto createRecipe(CreateRecipeReqDto req) {
    // 1. 입력 검증
    if (req.getTitle() == null || req.getTitle().trim().isEmpty()) {
      throw new GustoException("RECIPE001"); // 레시피 제목은 필수입니다.
    }
    if (req.getTitle().length() > 120) {
      throw new GustoException("RECIPE004"); // 레시피 제목은 120자 이하여야 합니다.
    }
    if (req.getSource() != null && req.getSource().length() > 500) {
      throw new GustoException("RECIPE006"); // 레시피 출처는 500자 이하여야 합니다.
    }

    // 2. 중복 체크
    if (recipeJpaRepository.findByTitle(req.getTitle().trim()).isPresent()) {
      throw new GustoException("RECIPE005"); // 동일한 제목의 레시피가 이미 존재합니다.
    }

    // 3. Recipe 엔티티 생성 및 저장
    RecipeEntity recipeEntity = new RecipeEntity(
        req.getTitle().trim(),
        req.getSource() != null ? req.getSource().trim() : null);
    RecipeEntity savedRecipe = recipeJpaRepository.save(recipeEntity);

    // 4. 응답 DTO 생성
    return new CreateRecipeResDto(
        savedRecipe.getRecipeId(),
        savedRecipe.getTitle(),
        savedRecipe.getSource(),
        savedRecipe.getCreatedAt());
  }

  @Override
  @Transactional(readOnly = true)
  public SelectRecipeResDto selectRecipeById(Long recipeId) {
    // 1. Recipe 조회
    RecipeEntity recipe = recipeJpaRepository.findById(recipeId)
        .orElse(null);

    if (recipe == null) {
      return null;
    }

    // 2. recipe_i18n 정보 조회 (모든 locale)
    List<RecipeI18nEntity> i18nList = recipeI18nJpaRepository.findByRecipeId(recipeId);
    Map<String, RecipeLocaleInfoDto> localeInfoMap = new HashMap<>();
    for (RecipeI18nEntity i18n : i18nList) {
      localeInfoMap.put(i18n.getLocale(), new RecipeLocaleInfoDto(
          i18n.getDescription(),
          i18n.getInstructions()));
    }

    // 3. recipe_alias 정보 조회 (모든 locale)
    List<RecipeAliasEntity> aliasList = recipeAliasJpaRepository.findByRecipeId(recipeId);
    Map<String, List<RecipeAliasDto>> aliasMap = new HashMap<>();
    for (RecipeAliasEntity alias : aliasList) {
      aliasMap.computeIfAbsent(alias.getLocale(), k -> new ArrayList<>())
          .add(new RecipeAliasDto(alias.getAliasId(), alias.getAlias()));
    }

    // 4. 응답 DTO 생성
    return new SelectRecipeResDto(
        recipe.getRecipeId(),
        recipe.getTitle(),
        recipe.getSource(),
        localeInfoMap,
        aliasMap,
        recipe.getRequiredIngredientIds(),
        recipe.getOptionalIngredientIds(),
        recipe.getCreatedAt(),
        recipe.getUpdatedAt());
  }

  @Override
  @Transactional
  public UpdateRecipeResDto updateRecipe(UpdateRecipeReqDto req) {
    // 1. Recipe 조회
    RecipeEntity recipe = recipeJpaRepository.findById(req.getRecipeId())
        .orElseThrow(() -> new GustoException("RECIPE002")); // 레시피를 찾을 수 없습니다.

    // 2. 입력 검증
    if (req.getTitle() == null || req.getTitle().trim().isEmpty()) {
      throw new GustoException("RECIPE001"); // 레시피 제목은 필수입니다.
    }
    if (req.getTitle().length() > 120) {
      throw new GustoException("RECIPE004"); // 레시피 제목은 120자 이하여야 합니다.
    }
    if (req.getSource() != null && req.getSource().length() > 500) {
      throw new GustoException("RECIPE006"); // 레시피 출처는 500자 이하여야 합니다.
    }

    // 3. 중복 체크 (자기 자신 제외)
    recipeJpaRepository.findByTitle(req.getTitle().trim())
        .ifPresent(existing -> {
          if (!existing.getRecipeId().equals(req.getRecipeId())) {
            throw new GustoException("RECIPE005"); // 동일한 제목의 레시피가 이미 존재합니다.
          }
        });

    // 4. 엔티티 수정
    recipe.setTitle(req.getTitle().trim());
    recipe.setSource(req.getSource() != null ? req.getSource().trim() : null);

    // 5. 저장
    RecipeEntity updatedRecipe = recipeJpaRepository.save(recipe);

    // 6. 응답 DTO 생성
    return new UpdateRecipeResDto(
        updatedRecipe.getRecipeId(),
        updatedRecipe.getTitle(),
        updatedRecipe.getSource(),
        updatedRecipe.getUpdatedAt());
  }

  @Override
  @Transactional
  public DeleteRecipeResDto deleteRecipe(Long recipeId) {
    // 1. Recipe 조회
    RecipeEntity recipe = recipeJpaRepository.findById(recipeId)
        .orElseThrow(() -> new GustoException("RECIPE002")); // 레시피를 찾을 수 없습니다.

    // 2. Recipe 삭제 (CASCADE로 recipe_ingredient도 함께 삭제됨)
    recipeJpaRepository.delete(recipe);

    // 3. 응답 DTO 생성
    return new DeleteRecipeResDto(
        recipeId,
        "레시피가 성공적으로 삭제되었습니다.");
  }

  @Override
  @Transactional
  public CreateRecipeIngredientResDto createRecipeIngredient(CreateRecipeIngredientReqDto req) {
    // 1. Recipe 존재 확인
    recipeJpaRepository.findById(req.getRecipeId())
        .orElseThrow(() -> new GustoException("RECIPE002")); // 레시피를 찾을 수 없습니다.

    // 2. 재료 존재 확인
    ingredientJpaRepository.findById(req.getIngredientId())
        .orElseThrow(() -> new GustoException("INGR006")); // 재료를 찾을 수 없습니다.

    // 3. 중복 체크 (같은 레시피에 같은 재료가 이미 있는지 확인)
    recipeIngredientJpaRepository
        .findByRecipeIdAndIngredientId(req.getRecipeId(), req.getIngredientId())
        .ifPresent(existing -> {
          throw new GustoException("RECIPE008"); // 이미 레시피에 포함된 재료입니다.
        });

    // 4. RecipeIngredient 엔티티 생성 및 저장
    RecipeIngredientEntity ingredientEntity = new RecipeIngredientEntity(
        req.getRecipeId(),
        req.getIngredientId(),
        req.getRole(),
        req.getAmount(),
        req.getUnit(),
        req.getNote());
    RecipeIngredientEntity savedIngredient = recipeIngredientJpaRepository.save(ingredientEntity);

    // 5. 레시피 캐시 업데이트 (required_ingredient_ids, optional_ingredient_ids)
    recipeIngredientJpaRepository.refreshRecipeIngredientCache(req.getRecipeId());

    // 6. 응답 DTO 생성
    return new CreateRecipeIngredientResDto(
        savedIngredient.getRecipeIngredientId(),
        savedIngredient.getRecipeId(),
        savedIngredient.getIngredientId(),
        savedIngredient.getRole(),
        savedIngredient.getAmount(),
        savedIngredient.getUnit(),
        savedIngredient.getNote(),
        savedIngredient.getCreatedAt());
  }

  @Override
  @Transactional(readOnly = true)
  public SelectRecipeIngredientResDto selectRecipeIngredientById(Long recipeIngredientId) {
    // 1. RecipeIngredient 조회
    RecipeIngredientEntity ingredient = recipeIngredientJpaRepository.findById(recipeIngredientId)
        .orElse(null);

    if (ingredient == null) {
      return null;
    }

    // 2. 응답 DTO 생성
    return new SelectRecipeIngredientResDto(
        ingredient.getRecipeIngredientId(),
        ingredient.getRecipeId(),
        ingredient.getIngredientId(),
        ingredient.getRole(),
        ingredient.getAmount(),
        ingredient.getUnit(),
        ingredient.getNote(),
        ingredient.getCreatedAt());
  }

  @Override
  @Transactional
  public UpdateRecipeIngredientResDto updateRecipeIngredient(UpdateRecipeIngredientReqDto req) {
    // 1. RecipeIngredient 조회
    RecipeIngredientEntity ingredient = recipeIngredientJpaRepository.findById(req.getRecipeIngredientId())
        .orElseThrow(() -> new GustoException("RECIPE009")); // 레시피 재료를 찾을 수 없습니다.

    // 2. 입력 검증
    if (req.getUnit() != null && req.getUnit().length() > 30) {
      throw new GustoException("RECIPE005"); // 단위는 30자 이하여야 합니다.
    }
    if (req.getNote() != null && req.getNote().length() > 255) {
      throw new GustoException("RECIPE006"); // 비고는 255자 이하여야 합니다.
    }

    // 3. 엔티티 수정
    if (req.getRole() != null) {
      ingredient.setRole(req.getRole());
    }
    if (req.getAmount() != null) {
      ingredient.setAmount(req.getAmount());
    }
    if (req.getUnit() != null) {
      ingredient.setUnit(req.getUnit());
    }
    if (req.getNote() != null) {
      ingredient.setNote(req.getNote());
    }

    // 4. 저장
    RecipeIngredientEntity updatedIngredient = recipeIngredientJpaRepository.save(ingredient);

    // 5. 레시피 캐시 업데이트 (required_ingredient_ids, optional_ingredient_ids)
    recipeIngredientJpaRepository.refreshRecipeIngredientCache(ingredient.getRecipeId());

    // 6. 응답 DTO 생성
    return new UpdateRecipeIngredientResDto(
        updatedIngredient.getRecipeIngredientId(),
        updatedIngredient.getRecipeId(),
        updatedIngredient.getIngredientId(),
        updatedIngredient.getRole(),
        updatedIngredient.getAmount(),
        updatedIngredient.getUnit(),
        updatedIngredient.getNote());
  }

  @Override
  @Transactional
  public DeleteRecipeIngredientResDto deleteRecipeIngredient(Long recipeIngredientId) {
    // 1. RecipeIngredient 조회
    RecipeIngredientEntity ingredient = recipeIngredientJpaRepository.findById(recipeIngredientId)
        .orElseThrow(() -> new GustoException("RECIPE009")); // 레시피 재료를 찾을 수 없습니다.

    Long recipeId = ingredient.getRecipeId();

    // 2. RecipeIngredient 삭제
    recipeIngredientJpaRepository.delete(ingredient);

    // 3. 레시피 캐시 업데이트 (required_ingredient_ids, optional_ingredient_ids)
    recipeIngredientJpaRepository.refreshRecipeIngredientCache(recipeId);

    // 4. 응답 DTO 생성
    return new DeleteRecipeIngredientResDto(
        recipeIngredientId,
        "레시피 재료가 성공적으로 삭제되었습니다.");
  }

  @Override
  @Transactional(readOnly = true)
  public List<SelectRecipeListItemDto> selectAll() {
    List<RecipeEntity> recipes = recipeJpaRepository.findAll();

    return recipes.stream()
        .map(recipe -> new SelectRecipeListItemDto(
            recipe.getRecipeId(),
            recipe.getTitle(),
            recipe.getCreatedAt(),
            recipe.getUpdatedAt()))
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public UpdateRecipeI18nResDto updateRecipeI18n(UpdateRecipeI18nReqDto req) {
    // 1. Recipe 존재 확인
    recipeJpaRepository.findById(req.getRecipeId())
        .orElseThrow(() -> new GustoException("RECIPE002")); // 레시피를 찾을 수 없습니다.

    // 2. 입력 검증
    if (req.getLocale() == null || req.getLocale().trim().isEmpty()) {
      throw new GustoException("RECIPE010"); // 언어 코드는 필수입니다.
    }

    // 3. recipe_i18n 조회 또는 생성 (upsert)
    RecipeI18nEntity i18n = recipeI18nJpaRepository
        .findByRecipeIdAndLocale(req.getRecipeId(), req.getLocale())
        .orElse(new RecipeI18nEntity(
            req.getRecipeId(),
            req.getLocale(),
            null,
            null));

    // 4. 엔티티 수정
    i18n.setDescription(req.getDescription());
    i18n.setInstructions(req.getInstructions());

    // 5. 저장
    RecipeI18nEntity savedI18n = recipeI18nJpaRepository.save(i18n);

    // 6. 응답 DTO 생성
    return new UpdateRecipeI18nResDto(
        savedI18n.getRecipeId(),
        savedI18n.getLocale(),
        savedI18n.getDescription(),
        savedI18n.getInstructions(),
        savedI18n.getCreatedAt(),
        savedI18n.getUpdatedAt());
  }
}
