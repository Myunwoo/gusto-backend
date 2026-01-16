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

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/updateIngredient",
            produces = { "application/json" }
    )
    @Operation(
            summary = "재료 기본정보 수정",
            description = "재료의 기본정보를 수정합니다 (국문명, 썸네일, 활성화 여부)."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "재료 기본정보 수정 성공",
                    content = @Content(schema = @Schema(implementation = UpdateIngredientBasicResDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 (재료 없음, 유효성 검증 실패 등)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 오류",
                    content = @Content
            )
    })
    public ResponseEntity<UpdateIngredientBasicResDto> updateIngredient(@Valid @RequestBody UpdateIngredientBasicReqDto req) {
        UpdateIngredientBasicResDto res = ingredientAdminUseCase.updateIngredient(req);
        return ResponseEntity.ok(res);
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/updateIngredientI18n",
            produces = { "application/json" }
    )
    @Operation(
            summary = "재료 Locale별 정보 수정",
            description = "특정 재료의 locale별 이름 및 설명을 수정합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "재료 Locale별 정보 수정 성공",
                    content = @Content(schema = @Schema(implementation = UpdateIngredientI18nResDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 (재료 없음, locale 정보 없음, 중복된 이름, 유효성 검증 실패 등)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 오류",
                    content = @Content
            )
    })
    public ResponseEntity<UpdateIngredientI18nResDto> updateIngredientI18n(@Valid @RequestBody UpdateIngredientI18nReqDto req) {
        UpdateIngredientI18nResDto res = ingredientAdminUseCase.updateIngredientI18n(req);
        return ResponseEntity.ok(res);
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/updateAliasAll",
            produces = { "application/json" }
    )
    @Operation(
            summary = "재료 별칭 일괄 수정",
            description = "특정 재료의 특정 locale에 있는 모든 별칭을 일괄 수정합니다 (기존 별칭 삭제 후 새로 추가)."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "재료 별칭 일괄 수정 성공",
                    content = @Content(schema = @Schema(implementation = UpdateAliasAllResDto.class))
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
    public ResponseEntity<UpdateAliasAllResDto> updateAliasAll(@Valid @RequestBody UpdateAliasAllReqDto req) {
        UpdateAliasAllResDto res = ingredientAdminUseCase.updateAliasAll(req);
        return ResponseEntity.ok(res);
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/updateAlias",
            produces = { "application/json" }
    )
    @Operation(
            summary = "재료 별칭 개별 수정",
            description = "특정 별칭을 개별적으로 수정합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "재료 별칭 개별 수정 성공",
                    content = @Content(schema = @Schema(implementation = UpdateAliasResDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 (별칭 없음, 중복된 별칭, 유효성 검증 실패 등)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 오류",
                    content = @Content
            )
    })
    public ResponseEntity<UpdateAliasResDto> updateAlias(@Valid @RequestBody UpdateAliasReqDto req) {
        UpdateAliasResDto res = ingredientAdminUseCase.updateAlias(req);
        return ResponseEntity.ok(res);
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/deleteIngredient",
            produces = { "application/json" }
    )
    @Operation(
            summary = "재료 삭제",
            description = "재료를 삭제합니다 (관련 데이터도 함께 삭제됩니다)."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "재료 삭제 성공",
                    content = @Content(schema = @Schema(implementation = DeleteIngredientResDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 (재료 없음 등)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 오류",
                    content = @Content
            )
    })
    public ResponseEntity<DeleteIngredientResDto> deleteIngredient(
            @Parameter(description = "재료 ID", required = true, example = "1")
            @RequestParam("id") Long id
    ) {
        DeleteIngredientResDto res = ingredientAdminUseCase.deleteIngredient(id);
        return ResponseEntity.ok(res);
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/deleteIngredientI18n",
            produces = { "application/json" }
    )
    @Operation(
            summary = "재료 Locale별 정보 삭제",
            description = "특정 재료의 특정 locale 정보를 삭제합니다 (해당 locale의 별칭도 함께 삭제됩니다)."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "재료 Locale별 정보 삭제 성공",
                    content = @Content(schema = @Schema(implementation = DeleteIngredientI18nResDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 (재료 없음, locale 정보 없음 등)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 오류",
                    content = @Content
            )
    })
    public ResponseEntity<DeleteIngredientI18nResDto> deleteIngredientI18n(
            @Parameter(description = "재료 ID", required = true, example = "1")
            @RequestParam("id") Long id,
            
            @Parameter(description = "언어 코드", required = true, example = "ko-KR")
            @RequestParam("locale") String locale
    ) {
        DeleteIngredientI18nResDto res = ingredientAdminUseCase.deleteIngredientI18n(id, locale);
        return ResponseEntity.ok(res);
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/deleteAliasAll",
            produces = { "application/json" }
    )
    @Operation(
            summary = "재료 별칭 일괄 삭제",
            description = "특정 재료의 특정 locale에 있는 모든 별칭을 일괄 삭제합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "재료 별칭 일괄 삭제 성공",
                    content = @Content(schema = @Schema(implementation = DeleteAliasAllResDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 (재료 없음, locale 정보 없음, 별칭 없음 등)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 오류",
                    content = @Content
            )
    })
    public ResponseEntity<DeleteAliasAllResDto> deleteAliasAll(
            @Parameter(description = "재료 ID", required = true, example = "1")
            @RequestParam("id") Long id,
            
            @Parameter(description = "언어 코드", required = true, example = "ko-KR")
            @RequestParam("locale") String locale
    ) {
        DeleteAliasAllResDto res = ingredientAdminUseCase.deleteAliasAll(id, locale);
        return ResponseEntity.ok(res);
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/deleteAlias",
            produces = { "application/json" }
    )
    @Operation(
            summary = "재료 별칭 개별 삭제",
            description = "특정 별칭을 개별적으로 삭제합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "재료 별칭 개별 삭제 성공",
                    content = @Content(schema = @Schema(implementation = DeleteAliasResDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 (별칭 없음 등)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 오류",
                    content = @Content
            )
    })
    public ResponseEntity<DeleteAliasResDto> deleteAlias(
            @Parameter(description = "별칭 ID", required = true, example = "1")
            @RequestParam("aliasId") Long aliasId
    ) {
        DeleteAliasResDto res = ingredientAdminUseCase.deleteAlias(aliasId);
        return ResponseEntity.ok(res);
    }
}
