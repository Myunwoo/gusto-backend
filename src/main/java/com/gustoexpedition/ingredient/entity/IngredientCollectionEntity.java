package com.gustoexpedition.ingredient.entity;

import jakarta.persistence.*;

import java.time.Instant;

/**
 * packageName : com.gustoexpedition.ingredient.entity
 * fileName : IngredientCollectionEntity
 * author : fddsg
 * date : 2026-01-16
 * description : 재료 컬렉션 엔티티
 */
@Entity
@Table(name = "ingredient_collection")
public class IngredientCollectionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "collection_id")
  private Long collectionId;

  @Column(name = "title", nullable = false, length = 120)
  private String title;

  @Column(name = "note", columnDefinition = "TEXT")
  private String note;

  @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
  private Instant createdAt;

  @Column(name = "updated_at", nullable = false, insertable = false, updatable = false)
  private Instant updatedAt;

  protected IngredientCollectionEntity() {
  }

  public IngredientCollectionEntity(String title, String note) {
    this.title = title;
    this.note = note;
  }

  public Long getCollectionId() {
    return collectionId;
  }

  public String getTitle() {
    return title;
  }

  public String getNote() {
    return note;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setNote(String note) {
    this.note = note;
  }
}
