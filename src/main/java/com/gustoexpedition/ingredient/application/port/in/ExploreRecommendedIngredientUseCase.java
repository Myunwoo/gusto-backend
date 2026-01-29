package com.gustoexpedition.ingredient.application.port.in;

import java.util.Optional;

public interface ExploreRecommendedIngredientUseCase {

    /**
     * use_yn = 'Y'인 행의 ingredient_id 반환
     */
    Long getRecommendedIngredientId();
}
