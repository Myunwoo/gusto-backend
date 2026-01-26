package com.gustoexpedition.recipe.entity;

import jakarta.persistence.*;

import java.time.Instant;

/**
 * packageName    : com.gustoexpedition.recipe.entity
 * fileName       : RecipeAliasEntity
 * author         : fddsg
 * date           : 2026-01-20
 * description    : 레시피의 별칭(다국어) 엔티티
 */
@Entity
@Table(
        name = "recipe_alias",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_recipe_alias",
                columnNames = {"recipe_id", "locale", "alias"}
        )
)
public class RecipeAliasEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alias_id")
    private Long aliasId;

    @Column(name = "recipe_id", nullable = false)
    private Long recipeId;

    @Column(name = "locale", nullable = false, length = 10)
    private String locale;

    @Column(name = "alias", nullable = false, length = 100)
    private String alias;

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", insertable = false, updatable = false)
    private RecipeEntity recipe;

    protected RecipeAliasEntity() {}

    public RecipeAliasEntity(Long recipeId, String locale, String alias) {
        this.recipeId = recipeId;
        this.locale = locale;
        this.alias = alias;
    }

    public Long getAliasId() {
        return aliasId;
    }

    public Long getRecipeId() {
        return recipeId;
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

