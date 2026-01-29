package com.gustoexpedition.ingredient.application.service;

import com.gustoexpedition.common.exception.GustoException;
import com.gustoexpedition.ingredient.adapter.out.persistence.ExploreRecommendedIngredientJpaRepository;
import com.gustoexpedition.ingredient.application.port.in.ExploreRecommendedIngredientUseCase;
import com.gustoexpedition.ingredient.entity.ExploreRecommendedIngredientEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Explore 페이지 기본 중앙 재료 조회.
 */
@Service
@RequiredArgsConstructor
public class ExploreRecommendedIngredientService implements ExploreRecommendedIngredientUseCase {

    private static final String USE_YN_Y = "Y";

    private final ExploreRecommendedIngredientJpaRepository exploreRecommendedIngredientJpaRepository;

    @Override
    @Transactional(readOnly = true)
    public Long getRecommendedIngredientId() {
        ExploreRecommendedIngredientEntity entity = exploreRecommendedIngredientJpaRepository
                .findTop1ByUseYnOrderByIdAsc(USE_YN_Y)
                .orElseThrow(() -> new GustoException("EXPLORE001"));
        return entity.getIngredientId();
    }
}
