package com.gustoexpedition.ingredient.adapter.in;

import com.gustoexpedition.ingredient.adapter.in.dto.*;
import com.gustoexpedition.ingredient.application.port.in.IngredientAdminUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/api/admin/ingredient")
@RequiredArgsConstructor
@Tag(name = "재료 관리 ADMIN", description = "재료 생성 및 조회 API")
public class IngredientAdminController {
    private final IngredientAdminUseCase ingredientAdminUseCase;

    @RequestMapping(
        method = RequestMethod.POST,
        value = "/createIngredient",
        produces = { "application/json" }
    )
    @Operation(
            summary = "재료 기본정보 생성",
            description = "재료의 기본정보만 생성합니다 (썸네일, 활성화 여부)."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "재료 기본정보 생성 성공",
                    content = @Content(schema = @Schema(implementation = CreateIngredientBasicResDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 (유효성 검증 실패 등)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 오류",
                    content = @Content
            )
    })
    public ResponseEntity<CreateIngredientBasicResDto> createIngredient(@Valid @RequestBody CreateIngredientBasicReqDto req) {
        CreateIngredientBasicResDto res = ingredientAdminUseCase.createIngredient(req);
        return ResponseEntity.ok(res);
    }

    @RequestMapping(
        method = RequestMethod.POST,
        value = "/createIngredientI18n",
        produces = { "application/json" }
    )
    @Operation(
            summary = "재료 locale별 기본정보 생성",
            description = "특정 재료에 locale별 기본정보를 추가합니다 (이름, 설명)."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "재료 locale별 기본정보 생성 성공",
                    content = @Content(schema = @Schema(implementation = CreateIngredientI18nResDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 (재료 없음, 중복된 locale/이름 조합, 유효성 검증 실패 등)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 오류",
                    content = @Content
            )
    })
    public ResponseEntity<CreateIngredientI18nResDto> createIngredientI18n(@Valid @RequestBody CreateIngredientI18nReqDto req) {
        CreateIngredientI18nResDto res = ingredientAdminUseCase.createIngredientI18n(req);
        return ResponseEntity.ok(res);
    }

    @RequestMapping(
        method = RequestMethod.POST,
        value = "/createAlias",
        produces = { "application/json" }
    )
    @Operation(
            summary = "재료 별칭 생성",
            description = "특정 재료의 특정 locale에 별칭을 추가합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "재료 별칭 생성 성공",
                    content = @Content(schema = @Schema(implementation = CreateAliasResDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 (재료 없음, locale 정보 없음, 유효성 검증 실패 등)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 오류",
                    content = @Content
            )
    })
    public ResponseEntity<CreateAliasResDto> createAlias(@Valid @RequestBody CreateAliasReqDto req) {
        CreateAliasResDto res = ingredientAdminUseCase.createAlias(req);
        return ResponseEntity.ok(res);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/selectById",
            produces = { "application/json" }
    )
    @Operation(
            summary = "재료 조회",
            description = "재료 ID로 재료 정보를 조회합니다. includeRelationYn - 관계 정보 포함 여부"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "재료 조회 성공",
                    content = @Content(schema = @Schema(implementation = SelectIngredientResDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 (유효하지 않은 ID 등)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 오류",
                    content = @Content
            )
    })
    public ResponseEntity<SelectIngredientResDto> selectById(
            @Parameter(description = "재료 ID", required = true, example = "1")
            @RequestParam("id") Long id,
            
            @Parameter(description = "언어 코드 (기본값: ko-KR)", example = "ko-KR")
            @RequestParam(value = "locale", required = false) String locale,
            
            @Parameter(description = "관계 정보 포함 여부 (기본값: false)", example = "true")
            @RequestParam(value = "includeRelationYn", required = false) Boolean includeRelationYn
    ) {
        SelectIngredientResDto res = ingredientAdminUseCase.selectById(id, locale, includeRelationYn);
        return ResponseEntity.ok(res);
    }
}
