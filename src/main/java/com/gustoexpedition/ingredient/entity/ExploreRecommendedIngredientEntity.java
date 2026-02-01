package com.gustoexpedition.ingredient.entity;

import jakarta.persistence.*;

import java.time.Instant;

/**
 * Explore 페이지 기본 중앙 재료(가장 마인드맵을 넓게 펼칠 수 있는 재료).
 * use_yn = 'Y'인 행은 하나만 유지.
 */
@Entity
@Table(name = "explore_recommended_ingredient")
public class ExploreRecommendedIngredientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ingredient_id", nullable = false)
    private Long ingredientId;

    @Column(name = "use_yn", nullable = false, length = 1, columnDefinition = "bpchar(1)")
    private String useYn = "Y";

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private Instant createdAt;

    protected ExploreRecommendedIngredientEntity() {
    }

    public ExploreRecommendedIngredientEntity(Long ingredientId, String useYn) {
        this.ingredientId = ingredientId;
        this.useYn = useYn != null ? useYn : "Y";
    }

    public Long getId() {
        return id;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public String getUseYn() {
        return useYn;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }
}
