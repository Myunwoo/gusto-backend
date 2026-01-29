package com.gustoexpedition.explore.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 마인드맵 트리 노드 (depth1/2/3 공통).
 */
@Getter
@AllArgsConstructor
@Schema(description = "마인드맵 트리 노드")
public class MindmapTreeNodeDto {

    @Schema(description = "재료 ID", example = "1")
    private Long ingredientId;

    @Schema(description = "표시 이름 (요청 locale 기준)", example = "바질")
    private String name;

    @Schema(description = "연관도 0~1", example = "0.92")
    private double weight;

    @Schema(description = "good(궁합) / warn(비궁합)", example = "good")
    private String type;

    @Schema(description = "깊이 1/2/3", example = "1")
    private int depth;

    @Schema(description = "부모 재료 ID (depth 2·3만)")
    private Long parentIngredientId;
}
