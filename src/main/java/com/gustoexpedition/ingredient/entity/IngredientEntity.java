package com.gustoexpedition.ingredient.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

/**
 * packageName    : com.gustoexpedition.ingredient.entity
 * fileName       : IngredientEntity
 * author         : fddsg
 * date           : 2026-01-14
 * description    : V2 스키마에 맞춘 Ingredient 엔티티
 */
@Entity
@Table(name = "ingredient")
public class IngredientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id")
    private Long ingredientId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false, insertable = false, updatable = false)
    private Instant updatedAt;

    protected IngredientEntity() {}

    public IngredientEntity(String name, String thumbnailUrl, Boolean isActive) {
        this.name = name;
        this.thumbnailUrl = thumbnailUrl;
        this.isActive = isActive != null ? isActive : true;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public String getName() {
        return name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
