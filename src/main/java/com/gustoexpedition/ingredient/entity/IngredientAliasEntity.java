package com.gustoexpedition.ingredient.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

/**
 * packageName    : com.gustoexpedition.ingredient.entity
 * fileName       : IngredientAliasEntity
 * author         : fddsg
 * date           : 2026-01-14
 * description    : 재료의 별칭(다국어) 엔티티
 */
@Entity
@Table(
        name = "ingredient_alias",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_ingredient_alias",
                columnNames = {"ingredient_id", "locale", "alias"}
        )
)
public class IngredientAliasEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alias_id")
    private Long aliasId;

    @Column(name = "ingredient_id", nullable = false)
    private Long ingredientId;

    @Column(name = "locale", nullable = false, length = 10)
    private String locale;

    @Column(name = "alias", nullable = false, length = 100)
    private String alias;

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", insertable = false, updatable = false)
    private IngredientEntity ingredient;

    protected IngredientAliasEntity() {}

    public IngredientAliasEntity(Long ingredientId, String locale, String alias) {
        this.ingredientId = ingredientId;
        this.locale = locale;
        this.alias = alias;
    }

    public Long getAliasId() {
        return aliasId;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public String getLocale() {
        return locale;
    }

    public String getAlias() {
        return alias;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
