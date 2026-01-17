package com.gustoexpedition.ingredient.entity;

import jakarta.persistence.*;

import java.time.Instant;

/**
 * packageName : com.gustoexpedition.ingredient.entity
 * fileName : IngredientCollectionItemEntity
 * author : fddsg
 * date : 2026-01-16
 * description : 재료 컬렉션 아이템 엔티티
 */
@Entity
@Table(name = "ingredient_collection_item", uniqueConstraints = @UniqueConstraint(name = "uk_collection_item", columnNames = {
    "collection_id", "ingredient_id" }))
public class IngredientCollectionItemEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "collection_item_id")
  private Long collectionItemId;

  @Column(name = "collection_id", nullable = false)
  private Long collectionId;

  @Column(name = "ingredient_id", nullable = false)
  private Long ingredientId;

  @Column(name = "added_order")
  private Integer addedOrder;

  @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
  private Instant createdAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "collection_id", insertable = false, updatable = false)
  private IngredientCollectionEntity collection;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ingredient_id", insertable = false, updatable = false)
  private IngredientEntity ingredient;

  protected IngredientCollectionItemEntity() {
  }

  public IngredientCollectionItemEntity(Long collectionId, Long ingredientId, Integer addedOrder) {
    this.collectionId = collectionId;
    this.ingredientId = ingredientId;
    this.addedOrder = addedOrder;
  }

  public Long getCollectionItemId() {
    return collectionItemId;
  }

  public Long getCollectionId() {
    return collectionId;
  }

  public Long getIngredientId() {
    return ingredientId;
  }

  public Integer getAddedOrder() {
    return addedOrder;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public IngredientCollectionEntity getCollection() {
    return collection;
  }

  public IngredientEntity getIngredient() {
    return ingredient;
  }

  public void setAddedOrder(Integer addedOrder) {
    this.addedOrder = addedOrder;
  }
}
