package com.gustoexpedition.ingredient.adapter.out.persistence;

import com.gustoexpedition.ingredient.entity.ExploreRecommendedIngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExploreRecommendedIngredientJpaRepository extends JpaRepository<ExploreRecommendedIngredientEntity, Long> {

    Optional<ExploreRecommendedIngredientEntity> findTop1ByUseYnOrderByIdAsc(String useYn);
}
