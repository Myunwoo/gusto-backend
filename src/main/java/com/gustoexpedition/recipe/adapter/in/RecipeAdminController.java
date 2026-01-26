package com.gustoexpedition.recipe.adapter.in;

import com.gustoexpedition.common.annotation.RequireAdmin;
import com.gustoexpedition.recipe.adapter.in.dto.*;
import com.gustoexpedition.recipe.application.port.in.RecipeAdminUseCase;
import com.gustoexpedition.recipe.application.port.in.RecipeAliasUseCase;
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
@RequestMapping("/api/admin/recipe")
@RequiredArgsConstructor
@RequireAdmin
@Tag(name = "레시피 관리 ADMIN", description = "레시피 생성 및 조회 API")
public class RecipeAdminController {
  private final RecipeAdminUseCase recipeAdminUseCase;
  private final RecipeAliasUseCase recipeAliasUseCase;

  @RequestMapping(method = RequestMethod.POST, value = "/createRecipe", produces = { "application/json" })
  @Operation(summary = "레시피 생성", description = "레시피를 생성합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "레시피 생성 성공", content = @Content(schema = @Schema(implementation = CreateRecipeResDto.class))),
      @ApiResponse(responseCode = "400", description = "잘못된 요청 (유효성 검증 실패 등)", content = @Content),
      @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
  })
  public ResponseEntity<CreateRecipeResDto> createRecipe(@Valid @RequestBody CreateRecipeReqDto req) {
    CreateRecipeResDto res = recipeAdminUseCase.createRecipe(req);
    return ResponseEntity.ok(res);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/selectAll", produces = { "application/json" })
  @Operation(summary = "레시피 목록 조회", description = "모든 레시피를 조회합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "레시피 목록 조회 성공", content = @Content(schema = @Schema(implementation = SelectRecipeListItemDto.class))),
      @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
  })
  public ResponseEntity<java.util.List<SelectRecipeListItemDto>> selectAll() {
    java.util.List<SelectRecipeListItemDto> res = recipeAdminUseCase.selectAll();
    return ResponseEntity.ok(res);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/selectRecipeById", produces = { "application/json" })
  @Operation(summary = "레시피 조회", description = "레시피 ID로 레시피 정보를 조회합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "레시피 조회 성공", content = @Content(schema = @Schema(implementation = SelectRecipeResDto.class))),
      @ApiResponse(responseCode = "400", description = "잘못된 요청 (유효하지 않은 ID 등)", content = @Content),
      @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
  })
  public ResponseEntity<SelectRecipeResDto> selectRecipeById(
      @Parameter(description = "레시피 ID", required = true, example = "1") @RequestParam("recipeId") Long recipeId) {
    SelectRecipeResDto res = recipeAdminUseCase.selectRecipeById(recipeId);
    return ResponseEntity.ok(res);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/updateRecipe", produces = { "application/json" })
  @Operation(summary = "레시피 수정", description = "레시피 제목을 수정합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "레시피 수정 성공", content = @Content(schema = @Schema(implementation = UpdateRecipeResDto.class))),
      @ApiResponse(responseCode = "400", description = "잘못된 요청 (레시피 없음, 유효성 검증 실패 등)", content = @Content),
      @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
  })
  public ResponseEntity<UpdateRecipeResDto> updateRecipe(@Valid @RequestBody UpdateRecipeReqDto req) {
    UpdateRecipeResDto res = recipeAdminUseCase.updateRecipe(req);
    return ResponseEntity.ok(res);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/updateRecipeI18n", produces = { "application/json" })
  @Operation(summary = "레시피 Locale별 정보 수정", description = "특정 레시피의 locale별 이름 및 설명을 수정합니다 (upsert: 없으면 생성, 있으면 수정).")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "레시피 Locale별 정보 수정 성공", content = @Content(schema = @Schema(implementation = UpdateRecipeI18nResDto.class))),
      @ApiResponse(responseCode = "400", description = "잘못된 요청 (레시피 없음, locale 정보 없음, 유효성 검증 실패 등)", content = @Content),
      @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
  })
  public ResponseEntity<UpdateRecipeI18nResDto> updateRecipeI18n(
      @Valid @RequestBody UpdateRecipeI18nReqDto req) {
    UpdateRecipeI18nResDto res = recipeAdminUseCase.updateRecipeI18n(req);
    return ResponseEntity.ok(res);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/deleteRecipe", produces = { "application/json" })
  @Operation(summary = "레시피 삭제", description = "레시피를 삭제합니다 (관련 데이터도 함께 삭제됩니다).")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "레시피 삭제 성공", content = @Content(schema = @Schema(implementation = DeleteRecipeResDto.class))),
      @ApiResponse(responseCode = "400", description = "잘못된 요청 (레시피 없음 등)", content = @Content),
      @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
  })
  public ResponseEntity<DeleteRecipeResDto> deleteRecipe(
      @Parameter(description = "레시피 ID", required = true, example = "1") @RequestParam("recipeId") Long recipeId) {
    DeleteRecipeResDto res = recipeAdminUseCase.deleteRecipe(recipeId);
    return ResponseEntity.ok(res);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/createRecipeIngredient", produces = { "application/json" })
  @Operation(summary = "레시피 재료 추가", description = "레시피에 재료를 추가합니다. 추가 후 레시피의 캐시(required_ingredient_ids, optional_ingredient_ids)가 자동으로 업데이트됩니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "레시피 재료 추가 성공", content = @Content(schema = @Schema(implementation = CreateRecipeIngredientResDto.class))),
      @ApiResponse(responseCode = "400", description = "잘못된 요청 (레시피 없음, 재료 없음, 이미 포함된 재료, 유효성 검증 실패 등)", content = @Content),
      @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
  })
  public ResponseEntity<CreateRecipeIngredientResDto> createRecipeIngredient(
      @Valid @RequestBody CreateRecipeIngredientReqDto req) {
    CreateRecipeIngredientResDto res = recipeAdminUseCase.createRecipeIngredient(req);
    return ResponseEntity.ok(res);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/selectRecipeIngredientById", produces = { "application/json" })
  @Operation(summary = "레시피 재료 조회", description = "레시피 재료 ID로 레시피 재료 정보를 조회합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "레시피 재료 조회 성공", content = @Content(schema = @Schema(implementation = SelectRecipeIngredientResDto.class))),
      @ApiResponse(responseCode = "400", description = "잘못된 요청 (유효하지 않은 ID 등)", content = @Content),
      @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
  })
  public ResponseEntity<SelectRecipeIngredientResDto> selectRecipeIngredientById(
      @Parameter(description = "레시피 재료 ID", required = true, example = "1") @RequestParam("recipeIngredientId") Long recipeIngredientId) {
    SelectRecipeIngredientResDto res = recipeAdminUseCase.selectRecipeIngredientById(recipeIngredientId);
    return ResponseEntity.ok(res);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/updateRecipeIngredient", produces = { "application/json" })
  @Operation(summary = "레시피 재료 수정", description = "레시피 재료 정보를 수정합니다. 수정 후 레시피의 캐시(required_ingredient_ids, optional_ingredient_ids)가 자동으로 업데이트됩니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "레시피 재료 수정 성공", content = @Content(schema = @Schema(implementation = UpdateRecipeIngredientResDto.class))),
      @ApiResponse(responseCode = "400", description = "잘못된 요청 (레시피 재료 없음, 유효성 검증 실패 등)", content = @Content),
      @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
  })
  public ResponseEntity<UpdateRecipeIngredientResDto> updateRecipeIngredient(
      @Valid @RequestBody UpdateRecipeIngredientReqDto req) {
    UpdateRecipeIngredientResDto res = recipeAdminUseCase.updateRecipeIngredient(req);
    return ResponseEntity.ok(res);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/deleteRecipeIngredient", produces = { "application/json" })
  @Operation(summary = "레시피 재료 삭제", description = "레시피에서 재료를 제거합니다. 삭제 후 레시피의 캐시(required_ingredient_ids, optional_ingredient_ids)가 자동으로 업데이트됩니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "레시피 재료 삭제 성공", content = @Content(schema = @Schema(implementation = DeleteRecipeIngredientResDto.class))),
      @ApiResponse(responseCode = "400", description = "잘못된 요청 (레시피 재료 없음 등)", content = @Content),
      @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
  })
  public ResponseEntity<DeleteRecipeIngredientResDto> deleteRecipeIngredient(
      @Parameter(description = "레시피 재료 ID", required = true, example = "1") @RequestParam("recipeIngredientId") Long recipeIngredientId) {
    DeleteRecipeIngredientResDto res = recipeAdminUseCase.deleteRecipeIngredient(recipeIngredientId);
    return ResponseEntity.ok(res);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/createAlias", produces = { "application/json" })
  @Operation(summary = "레시피 별칭 생성", description = "특정 레시피(recipe)의 특정 locale에 레시피 별칭을 추가합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "레시피 별칭 생성 성공", content = @Content(schema = @Schema(implementation = CreateAliasResDto.class))),
      @ApiResponse(responseCode = "400", description = "잘못된 요청 (레시피 없음, locale 정보 없음, 유효성 검증 실패 등)", content = @Content),
      @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
  })
  public ResponseEntity<CreateAliasResDto> createAlias(@Valid @RequestBody CreateAliasReqDto req) {
    CreateAliasResDto res = recipeAliasUseCase.createAlias(req);
    return ResponseEntity.ok(res);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/updateAliasAll", produces = { "application/json" })
  @Operation(summary = "레시피 별칭 일괄 수정", description = "특정 레시피(recipe)의 특정 locale에 있는 모든 레시피 별칭을 일괄 수정합니다 (기존 레시피 별칭 삭제 후 새로 추가).")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "레시피 별칭 일괄 수정 성공", content = @Content(schema = @Schema(implementation = UpdateAliasAllResDto.class))),
      @ApiResponse(responseCode = "400", description = "잘못된 요청 (레시피 없음, locale 정보 없음, 유효성 검증 실패 등)", content = @Content),
      @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
  })
  public ResponseEntity<UpdateAliasAllResDto> updateAliasAll(@Valid @RequestBody UpdateAliasAllReqDto req) {
    UpdateAliasAllResDto res = recipeAliasUseCase.updateAliasAll(req);
    return ResponseEntity.ok(res);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/updateAlias", produces = { "application/json" })
  @Operation(summary = "레시피 별칭 개별 수정", description = "특정 레시피(recipe) 별칭을 개별적으로 수정합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "레시피 별칭 개별 수정 성공", content = @Content(schema = @Schema(implementation = UpdateAliasResDto.class))),
      @ApiResponse(responseCode = "400", description = "잘못된 요청 (별칭 없음, 중복된 별칭, 유효성 검증 실패 등)", content = @Content),
      @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
  })
  public ResponseEntity<UpdateAliasResDto> updateAlias(@Valid @RequestBody UpdateAliasReqDto req) {
    UpdateAliasResDto res = recipeAliasUseCase.updateAlias(req);
    return ResponseEntity.ok(res);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/deleteAliasAll", produces = { "application/json" })
  @Operation(summary = "레시피 별칭 일괄 삭제", description = "특정 레시피(recipe)의 특정 locale에 있는 모든 레시피 별칭을 일괄 삭제합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "레시피 별칭 일괄 삭제 성공", content = @Content(schema = @Schema(implementation = DeleteAliasAllResDto.class))),
      @ApiResponse(responseCode = "400", description = "잘못된 요청 (레시피 없음, locale 정보 없음, 별칭 없음 등)", content = @Content),
      @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
  })
  public ResponseEntity<DeleteAliasAllResDto> deleteAliasAll(
      @Parameter(description = "레시피 ID", required = true, example = "1") @RequestParam("recipeId") Long recipeId,
      @Parameter(description = "언어 코드", required = true, example = "ko-KR") @RequestParam("locale") String locale) {
    DeleteAliasAllResDto res = recipeAliasUseCase.deleteAliasAll(recipeId, locale);
    return ResponseEntity.ok(res);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/deleteAlias", produces = { "application/json" })
  @Operation(summary = "레시피 별칭 개별 삭제", description = "특정 레시피(recipe) 별칭을 개별적으로 삭제합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "레시피 별칭 개별 삭제 성공", content = @Content(schema = @Schema(implementation = DeleteAliasResDto.class))),
      @ApiResponse(responseCode = "400", description = "잘못된 요청 (레시피 별칭 없음 등)", content = @Content),
      @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
  })
  public ResponseEntity<DeleteAliasResDto> deleteAlias(
      @Parameter(description = "레시피 별칭 ID", required = true, example = "1") @RequestParam("aliasId") Long aliasId) {
    DeleteAliasResDto res = recipeAliasUseCase.deleteAlias(aliasId);
    return ResponseEntity.ok(res);
  }
}
