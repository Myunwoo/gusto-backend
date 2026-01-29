package com.gustoexpedition.explore.adapter.in;

import com.gustoexpedition.explore.adapter.in.dto.MindmapTreeResDto;
import com.gustoexpedition.explore.application.port.in.ExploreMindmapTreeUseCase;
import com.gustoexpedition.ingredient.adapter.in.dto.ExploreRecommendedIngredientIdResDto;
import com.gustoexpedition.ingredient.application.port.in.ExploreRecommendedIngredientUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/explore")
@RequiredArgsConstructor
@Tag(name = "Explore", description = "Explore(재료 마인드맵) API. 비로그인 가능.")
public class ExploreController {

    private final ExploreRecommendedIngredientUseCase exploreRecommendedIngredientUseCase;
    private final ExploreMindmapTreeUseCase exploreMindmapTreeUseCase;

    @RequestMapping(method = RequestMethod.GET, value = "/recommendedIngredientId", produces = { "application/json" })
    @Operation(summary = "Explore 추천 재료 ID", description = "Explore 페이지 기본 중앙 재료(가장 마인드맵을 넓게 펼칠 수 있는 재료)의 ingredient_id를 반환합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = ExploreRecommendedIngredientIdResDto.class))),
        @ApiResponse(responseCode = "400", description = "추천 재료 없음 (EXPLORE001)", content = @Content),
        @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    public ResponseEntity<ExploreRecommendedIngredientIdResDto> getRecommendedIngredientId() {
        Long ingredientId = exploreRecommendedIngredientUseCase.getRecommendedIngredientId();
        return ResponseEntity.ok(new ExploreRecommendedIngredientIdResDto(ingredientId));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/mindmap-tree/{ingredientId}", produces = { "application/json" })
    @Operation(summary = "마인드맵 트리", description = "중앙 재료(ingredientId) 기준 3-depth 연관 트리를 반환합니다. locale별 name 적용.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = MindmapTreeResDto.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
        @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    public ResponseEntity<MindmapTreeResDto> getMindmapTree(
        @Parameter(description = "중앙 재료 ID", required = true, example = "1") @PathVariable Long ingredientId,
        @Parameter(description = "locale (다국어 name)", example = "ko-KR") @RequestParam(value = "locale", required = false) String locale) {
        MindmapTreeResDto res = exploreMindmapTreeUseCase.getTree(ingredientId, locale);
        return ResponseEntity.ok(res);
    }
}
