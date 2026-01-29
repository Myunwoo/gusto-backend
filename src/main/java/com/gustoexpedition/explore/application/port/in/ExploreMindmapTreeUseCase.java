package com.gustoexpedition.explore.application.port.in;

import com.gustoexpedition.explore.adapter.in.dto.MindmapTreeResDto;

/**
 * Explore 마인드맵 트리 조회 (중앙 재료 기준 3-depth).
 */
public interface ExploreMindmapTreeUseCase {

    /**
     * ingredientId를 중앙으로 하는 연관 트리 반환 (locale별 name 적용).
     */
    MindmapTreeResDto getTree(Long ingredientId, String locale);
}
