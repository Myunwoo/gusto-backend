package com.gustoexpedition.explore.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * 마인드맵 트리 응답 (depth1, depth2, depth3).
 */
@Getter
@AllArgsConstructor
@Schema(description = "마인드맵 트리 응답")
public class MindmapTreeResDto {

    @Schema(description = "1-depth 노드 (중앙 직접 연관)")
    private List<MindmapTreeNodeDto> depth1;

    @Schema(description = "2-depth 노드")
    private List<MindmapTreeNodeDto> depth2;

    @Schema(description = "3-depth 노드")
    private List<MindmapTreeNodeDto> depth3;
}
