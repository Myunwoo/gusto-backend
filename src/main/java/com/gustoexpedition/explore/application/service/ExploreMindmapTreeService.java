package com.gustoexpedition.explore.application.service;

import com.gustoexpedition.explore.adapter.in.dto.MindmapTreeNodeDto;
import com.gustoexpedition.explore.adapter.in.dto.MindmapTreeResDto;
import com.gustoexpedition.explore.application.port.in.ExploreMindmapTreeUseCase;
import com.gustoexpedition.ingredient.adapter.out.persistence.IngredientEdgeJpaRepository;
import com.gustoexpedition.ingredient.adapter.out.persistence.IngredientI18nJpaRepository;
import com.gustoexpedition.ingredient.entity.IngredientEdgeEntity;
import com.gustoexpedition.ingredient.entity.IngredientI18nId;
import com.gustoexpedition.ingredient.entity.IngredientI18nEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Explore 마인드맵 트리 조회 (중앙 재료 기준 3-depth).
 * EXPEDITION: depth1 top 8, weight>=T2면 depth2, weight>=T3면 depth3.
 */
@Service
@RequiredArgsConstructor
public class ExploreMindmapTreeService implements ExploreMindmapTreeUseCase {

    private static final int DEPTH1_MAX = 8;
    private static final int DEPTH2_MAX = 10;
    private static final int DEPTH3_MAX = 8;
    private static final double T2 = 0.65;
    private static final double T3 = 0.75;
    private static final String DEFAULT_LOCALE = "ko-KR";

    private final IngredientEdgeJpaRepository ingredientEdgeJpaRepository;
    private final IngredientI18nJpaRepository ingredientI18nJpaRepository;

    @Override
    @Transactional(readOnly = true)
    public MindmapTreeResDto getTree(Long ingredientId, String locale) {
        String loc = (locale != null && !locale.isBlank()) ? locale.trim() : DEFAULT_LOCALE;

        List<IngredientEdgeEntity> centerEdges = ingredientEdgeJpaRepository
                .findByFromIngredientIdOrToIngredientIdOrderByScoreDesc(ingredientId, ingredientId);

        // depth1: 중앙과 연결된 노드, 상위 N개, weight = score/10, type = good/warn
        Map<Long, MindmapTreeNodeDto> neighborMap = new LinkedHashMap<>();
        for (IngredientEdgeEntity e : centerEdges) {
            Long otherId = e.getFromIngredientId().equals(ingredientId) ? e.getToIngredientId() : e.getFromIngredientId();
            double w = toWeight(e.getScore(), e.getRelationType());
            String type = toType(e.getRelationType());
            if (e.getRelationType() == IngredientEdgeEntity.IngredientRelationType.NEUTRAL) {
                continue; // NEUTRAL은 마인드맵에서 제외하거나 good으로 포함 가능. 여기서는 제외.
            }
            neighborMap.merge(otherId, new MindmapTreeNodeDto(otherId, getName(otherId, loc), w, type, 1, null),
                    (a, b) -> a.getWeight() >= b.getWeight() ? a : b);
        }
        List<MindmapTreeNodeDto> depth1 = neighborMap.values().stream()
                .sorted(Comparator.comparingDouble(MindmapTreeNodeDto::getWeight).reversed())
                .limit(DEPTH1_MAX)
                .collect(Collectors.toList());

        Set<Long> seen = new HashSet<>();
        seen.add(ingredientId);
        depth1.forEach(n -> seen.add(n.getIngredientId()));

        // depth2: depth1 중 weight>=T2인 노드의 이웃 (seen 제외)
        List<MindmapTreeNodeDto> depth2 = new ArrayList<>();
        for (MindmapTreeNodeDto d1 : depth1) {
            if (d1.getWeight() < T2 || depth2.size() >= DEPTH2_MAX) continue;
            List<IngredientEdgeEntity> edges = ingredientEdgeJpaRepository
                    .findByFromIngredientIdOrToIngredientIdOrderByScoreDesc(d1.getIngredientId(), d1.getIngredientId());
            for (IngredientEdgeEntity e : edges) {
                if (depth2.size() >= DEPTH2_MAX) break;
                Long otherId = e.getFromIngredientId().equals(d1.getIngredientId()) ? e.getToIngredientId() : e.getFromIngredientId();
                if (seen.contains(otherId)) continue;
                seen.add(otherId);
                double w = toWeight(e.getScore(), e.getRelationType());
                String type = toType(e.getRelationType());
                if (e.getRelationType() == IngredientEdgeEntity.IngredientRelationType.NEUTRAL) continue;
                depth2.add(new MindmapTreeNodeDto(otherId, getName(otherId, loc), w, type, 2, d1.getIngredientId()));
            }
        }

        // depth3: depth2 중 weight>=T3인 노드의 이웃
        List<MindmapTreeNodeDto> depth3 = new ArrayList<>();
        for (MindmapTreeNodeDto d2 : depth2) {
            if (d2.getWeight() < T3 || depth3.size() >= DEPTH3_MAX) continue;
            List<IngredientEdgeEntity> edges = ingredientEdgeJpaRepository
                    .findByFromIngredientIdOrToIngredientIdOrderByScoreDesc(d2.getIngredientId(), d2.getIngredientId());
            for (IngredientEdgeEntity e : edges) {
                if (depth3.size() >= DEPTH3_MAX) break;
                Long otherId = e.getFromIngredientId().equals(d2.getIngredientId()) ? e.getToIngredientId() : e.getFromIngredientId();
                if (seen.contains(otherId)) continue;
                seen.add(otherId);
                double w = toWeight(e.getScore(), e.getRelationType());
                String type = toType(e.getRelationType());
                if (e.getRelationType() == IngredientEdgeEntity.IngredientRelationType.NEUTRAL) continue;
                depth3.add(new MindmapTreeNodeDto(otherId, getName(otherId, loc), w, type, 3, d2.getIngredientId()));
            }
        }

        return new MindmapTreeResDto(depth1, depth2, depth3);
    }

    private static double toWeight(Integer score, IngredientEdgeEntity.IngredientRelationType relationType) {
        if (score == null) return 0.5;
        // score 1~10 -> weight 0.1~1.0
        return Math.max(0.1, Math.min(1.0, score / 10.0));
    }

    private static String toType(IngredientEdgeEntity.IngredientRelationType relationType) {
        if (relationType == null) return "good";
        return relationType == IngredientEdgeEntity.IngredientRelationType.AVOID ? "warn" : "good";
    }

    private String getName(Long ingredientId, String locale) {
        return ingredientI18nJpaRepository.findById(new IngredientI18nId(ingredientId, locale))
                .map(IngredientI18nEntity::getName)
                .orElse("");
    }
}
