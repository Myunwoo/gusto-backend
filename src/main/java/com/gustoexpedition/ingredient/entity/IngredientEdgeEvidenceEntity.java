package com.gustoexpedition.ingredient.entity;

import jakarta.persistence.*;

import java.time.Instant;

/**
 * packageName : com.gustoexpedition.ingredient.entity
 * fileName : IngredientEdgeEvidenceEntity
 * author : fddsg
 * date : 2026-01-16
 * description : 재료 간 관계의 증거/출처 엔티티
 */
@Entity
@Table(name = "ingredient_edge_evidence")
public class IngredientEdgeEvidenceEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "evidence_id")
  private Long evidenceId;

  @Column(name = "edge_id", nullable = false)
  private Long edgeId;

  @Enumerated(EnumType.STRING)
  @Column(name = "evidence_type", nullable = false, length = 50)
  private EdgeEvidenceType evidenceType;

  @Column(name = "title", length = 200)
  private String title;

  @Column(name = "content", columnDefinition = "TEXT")
  private String content;

  @Column(name = "source_ref", columnDefinition = "TEXT")
  private String sourceRef;

  @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
  private Instant createdAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "edge_id", insertable = false, updatable = false)
  private IngredientEdgeEntity edge;

  protected IngredientEdgeEvidenceEntity() {
  }

  public IngredientEdgeEvidenceEntity(Long edgeId, EdgeEvidenceType evidenceType, String title, String content,
      String sourceRef) {
    this.edgeId = edgeId;
    this.evidenceType = evidenceType;
    this.title = title;
    this.content = content;
    this.sourceRef = sourceRef;
  }

  public Long getEvidenceId() {
    return evidenceId;
  }

  public Long getEdgeId() {
    return edgeId;
  }

  public EdgeEvidenceType getEvidenceType() {
    return evidenceType;
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }

  public String getSourceRef() {
    return sourceRef;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public IngredientEdgeEntity getEdge() {
    return edge;
  }

  public void setEvidenceType(EdgeEvidenceType evidenceType) {
    this.evidenceType = evidenceType;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setSourceRef(String sourceRef) {
    this.sourceRef = sourceRef;
  }

  /**
   * 증거 타입 Enum
   */
  public enum EdgeEvidenceType {
    NOTE, // 노트
    BOOK, // 책
    VIDEO, // 비디오
    EXPERIMENT, // 실험
    RECIPE_REFERENCE, // 레시피 참조
    LINK // 링크
  }
}
