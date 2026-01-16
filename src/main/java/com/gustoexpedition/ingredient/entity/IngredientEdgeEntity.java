package com.gustoexpedition.ingredient.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * packageName    : com.gustoexpedition.ingredient.entity
 * fileName       : IngredientEdgeEntity
 * author         : fddsg
 * date           : 2026-01-15
 * description    : 재료 간 관계 엔티티
 */
@Entity
@Table(name = "ingredient_edge")
public class IngredientEdgeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "edge_id")
    private Long edgeId;

    @Column(name = "from_ingredient_id", nullable = false)
    private Long fromIngredientId;

    @Column(name = "to_ingredient_id", nullable = false)
    private Long toIngredientId;

    @Enumerated(EnumType.STRING)
    @Column(name = "relation_type", nullable = false)
    private IngredientRelationType relationType;

    @Column(name = "score", nullable = false, precision = 4, scale = 3)
    private BigDecimal score;

    @Column(name = "confidence", precision = 4, scale = 3)
    private BigDecimal confidence;

    @Column(name = "reason_summary", length = 255)
    private String reasonSummary;

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false, insertable = false, updatable = false)
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_ingredient_id", insertable = false, updatable = false)
    private IngredientEntity fromIngredient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_ingredient_id", insertable = false, updatable = false)
    private IngredientEntity toIngredient;

    protected IngredientEdgeEntity() {}

    public Long getEdgeId() {
        return edgeId;
    }

    public Long getFromIngredientId() {
        return fromIngredientId;
    }

    public Long getToIngredientId() {
        return toIngredientId;
    }

    public IngredientRelationType getRelationType() {
        return relationType;
    }

    public BigDecimal getScore() {
        return score;
    }

    public BigDecimal getConfidence() {
        return confidence;
    }

    public String getReasonSummary() {
        return reasonSummary;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public IngredientEntity getFromIngredient() {
        return fromIngredient;
    }

    public IngredientEntity getToIngredient() {
        return toIngredient;
    }

    /**
     * 관계 타입 Enum
     */
    public enum IngredientRelationType {
        PAIR_WELL,  // 궁합
        AVOID,      // 비궁합
        NEUTRAL     // 중립
    }
}
