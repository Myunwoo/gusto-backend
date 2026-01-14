package com.gustoexpedition.ingredient.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

/**
 * packageName    : com.gustoexpedition.ingredient.entity
 * fileName       : IngredientI18nEntity
 * author         : fddsg
 * date           : 2026-01-14
 * description    : 재료의 다국어 정보 엔티티
 */
@Entity
@Table(
        name = "ingredient_i18n",
        uniqueConstraints = @UniqueConstraint(
                name = "ux_ingredient_i18n_locale_name",
                columnNames = {"locale", "name"}
        )
)
@IdClass(IngredientI18nId.class)
public class IngredientI18nEntity {

    @Id
    @Column(name = "ingredient_id", nullable = false)
    private Long ingredientId;

    @Id
    @Column(name = "locale", nullable = false, length = 10)
    private String locale;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false, insertable = false, updatable = false)
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", insertable = false, updatable = false)
    private IngredientEntity ingredient;

    protected IngredientI18nEntity() {}

    public IngredientI18nEntity(Long ingredientId, String locale, String name, String description) {
        this.ingredientId = ingredientId;
        this.locale = locale;
        this.name = name;
        this.description = description;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public String getLocale() {
        return locale;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public IngredientEntity getIngredient() {
        return ingredient;
    }
}
