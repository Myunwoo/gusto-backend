package com.gustoexpedition.recipe.application.service;

import com.gustoexpedition.ingredient.adapter.out.persistence.IngredientJpaRepository;
import com.gustoexpedition.ingredient.entity.IngredientEntity;
import com.gustoexpedition.recipe.adapter.in.dto.MissingIngredientDto;
import com.gustoexpedition.recipe.adapter.in.dto.RecommendRecipeReqDto;
import com.gustoexpedition.recipe.adapter.in.dto.RecommendRecipeResDto;
import com.gustoexpedition.recipe.adapter.in.dto.RecipeRecommendationDto;
import com.gustoexpedition.recipe.adapter.out.persistence.RecipeJpaRepository;
import com.gustoexpedition.recipe.application.port.in.RecipeUseCase;
import com.gustoexpedition.recipe.entity.RecipeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * packageName : com.gustoexpedition.recipe.application.service
 * fileName : RecipeService
 * author : fddsg
 * date : 2026-01-16
 * description : 레시피 사용자용 서비스
 */
@Service
@RequiredArgsConstructor
public class RecipeService implements RecipeUseCase {

    private final RecipeJpaRepository recipeJpaRepository;
    private final IngredientJpaRepository ingredientJpaRepository;

    @Override
    @Transactional(readOnly = true)
    public RecommendRecipeResDto recommendRecipes(RecommendRecipeReqDto req) {
        // 1. 사용자가 선택한 재료 ID 목록
        Set<Long> userIngredientIds = new HashSet<>(req.getIngredientIds());

        // 2. 재료 ID 배열을 Integer 배열로 변환 (PostgreSQL int[] 타입)
        Integer[] ingredientIdsArray = req.getIngredientIds().stream()
                .map(Long::intValue)
                .toArray(Integer[]::new);

        // 3. GIN 인덱스를 활용하여 필수 재료가 사용자 재료에 포함되는 레시피 조회
        // (required_ingredient_ids && user_ingredient_ids)
        List<RecipeEntity> candidateRecipes = recipeJpaRepository.findRecommendableRecipes(ingredientIdsArray);

        // 4. 모든 부족한 재료 ID 수집 (재료 정보 조회를 위한 배치 처리)
        Set<Long> allMissingIngredientIds = new HashSet<>();
        candidateRecipes.forEach(recipe -> {
            Arrays.stream(recipe.getRequiredIngredientIds())
                    .map(Integer::longValue)
                    .filter(id -> !userIngredientIds.contains(id))
                    .forEach(allMissingIngredientIds::add);
            Arrays.stream(recipe.getOptionalIngredientIds())
                    .map(Integer::longValue)
                    .filter(id -> !userIngredientIds.contains(id))
                    .forEach(allMissingIngredientIds::add);
        });

        // 5. 부족한 재료 정보 일괄 조회 (ID -> 이름 매핑)
        Map<Long, String> ingredientNameMap = new HashMap<>();
        if (!allMissingIngredientIds.isEmpty()) {
            List<IngredientEntity> ingredients = ingredientJpaRepository.findAllById(allMissingIngredientIds);
            ingredientNameMap = ingredients.stream()
                    .collect(Collectors.toMap(
                            IngredientEntity::getIngredientId,
                            IngredientEntity::getName));
        }

        // 6. 각 레시피에 대해 우선순위 점수 계산 및 메타데이터 생성
        final Map<Long, String> finalIngredientNameMap = ingredientNameMap;
        List<RecipeRecommendationDto> recommendations = candidateRecipes.stream()
                .map(recipe -> calculateRecommendation(recipe, userIngredientIds, finalIngredientNameMap))
                .sorted((a, b) -> Integer.compare(b.getPriorityScore(), a.getPriorityScore())) // 우선순위 높은 순
                .limit(10) // 최대 10개
                .collect(Collectors.toList());

        return new RecommendRecipeResDto(recommendations);
    }

    /**
     * 레시피 추천 점수 및 메타데이터 계산
     */
    private RecipeRecommendationDto calculateRecommendation(RecipeEntity recipe, Set<Long> userIngredientIds,
            Map<Long, String> ingredientNameMap) {
        // 필수 재료 정보
        Set<Long> requiredIds = Arrays.stream(recipe.getRequiredIngredientIds())
                .map(Integer::longValue)
                .collect(Collectors.toSet());

        // 옵셔널 재료 정보
        Set<Long> optionalIds = Arrays.stream(recipe.getOptionalIngredientIds())
                .map(Integer::longValue)
                .collect(Collectors.toSet());

        // 부족한 필수 재료 계산 (ID와 이름 포함)
        List<MissingIngredientDto> missingRequired = requiredIds.stream()
                .filter(id -> !userIngredientIds.contains(id))
                .sorted()
                .map(id -> new MissingIngredientDto(id, ingredientNameMap.getOrDefault(id, "알 수 없는 재료")))
                .collect(Collectors.toList());

        // 부족한 옵셔널 재료 계산 (ID와 이름 포함)
        List<MissingIngredientDto> missingOptional = optionalIds.stream()
                .filter(id -> !userIngredientIds.contains(id))
                .sorted()
                .map(id -> new MissingIngredientDto(id, ingredientNameMap.getOrDefault(id, "알 수 없는 재료")))
                .collect(Collectors.toList());

        // 매칭률 계산
        int requiredMatchRate = requiredIds.isEmpty() ? 100
                : (int) ((double) (requiredIds.size() - missingRequired.size()) / requiredIds.size() * 100);

        int optionalMatchRate = optionalIds.isEmpty() ? 100
                : (int) ((double) (optionalIds.size() - missingOptional.size()) / optionalIds.size() * 100);

        // 우선순위 점수 계산 (0-100)
        // - 필수 재료 매칭률이 가장 중요 (70% 가중치)
        // - 옵셔널 재료 매칭률 (20% 가중치)
        // - 부족한 재료가 적을수록 높은 점수 (10% 가중치)
        int priorityScore = calculatePriorityScore(requiredMatchRate, optionalMatchRate,
                missingRequired.size(), missingOptional.size(), requiredIds.size(), optionalIds.size());

        return new RecipeRecommendationDto(
                recipe.getRecipeId(),
                recipe.getTitle(),
                recipe.getDescription(),
                priorityScore,
                missingRequired,
                missingOptional,
                requiredMatchRate,
                optionalMatchRate);
    }

    /**
     * 우선순위 점수 계산 (0-100)
     * 
     * 점수 구성:
     * - 필수 재료 매칭률: 70% 가중치
     * - 옵셔널 재료 매칭률: 20% 가중치
     * - 부족한 재료 페널티: 10% 가중치 (부족한 재료가 적을수록 높은 점수)
     */
    private int calculatePriorityScore(int requiredMatchRate, int optionalMatchRate,
            int missingRequiredCount, int missingOptionalCount,
            int totalRequiredCount, int totalOptionalCount) {
        // 필수 재료 매칭률 점수 (70점 만점)
        double requiredScore = requiredMatchRate * 0.7;

        // 옵셔널 재료 매칭률 점수 (20점 만점)
        double optionalScore = optionalMatchRate * 0.2;

        // 부족한 재료 페널티 점수 (10점 만점)
        // 부족한 재료가 없으면 10점, 많을수록 감점
        int totalMissing = missingRequiredCount + missingOptionalCount;
        int totalIngredients = totalRequiredCount + totalOptionalCount;
        double penaltyScore = totalIngredients == 0 ? 10.0
                : Math.max(0, 10.0 * (1.0 - (double) totalMissing / Math.max(totalIngredients, 1)));

        int totalScore = (int) Math.round(requiredScore + optionalScore + penaltyScore);
        return Math.min(100, Math.max(0, totalScore)); // 0-100 범위로 제한
    }
}
