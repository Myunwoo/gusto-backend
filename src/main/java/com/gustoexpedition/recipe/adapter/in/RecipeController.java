package com.gustoexpedition.recipe.adapter.in;

import com.gustoexpedition.recipe.adapter.in.dto.RecommendRecipeReqDto;
import com.gustoexpedition.recipe.adapter.in.dto.RecommendRecipeResDto;
import com.gustoexpedition.recipe.application.port.in.RecipeUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recipe")
@RequiredArgsConstructor
@Tag(name = "레시피 관리", description = "레시피 추천 API")
public class RecipeController {
    private final RecipeUseCase recipeUseCase;

    @RequestMapping(method = RequestMethod.POST, value = "/recommend", produces = { "application/json" })
    @Operation(summary = "레시피 추천", description = "사용자가 선택한 재료 목록으로 레시피를 추천합니다. 최대 10개까지 반환되며, 우선순위 메타데이터가 포함됩니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "레시피 추천 성공", content = @Content(schema = @Schema(implementation = RecommendRecipeResDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 (재료 목록이 비어있음, 유효성 검증 실패 등)", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    public ResponseEntity<RecommendRecipeResDto> recommendRecipes(@Valid @RequestBody RecommendRecipeReqDto req) {
        RecommendRecipeResDto res = recipeUseCase.recommendRecipes(req);
        return ResponseEntity.ok(res);
    }
}

