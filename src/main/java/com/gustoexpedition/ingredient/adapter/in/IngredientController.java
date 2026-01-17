package com.gustoexpedition.ingredient.adapter.in;

import com.gustoexpedition.ingredient.adapter.in.dto.*;
import com.gustoexpedition.ingredient.application.port.in.IngredientCollectionUseCase;
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
@RequestMapping("/api/ingredient")
@RequiredArgsConstructor
@Tag(name = "재료 관리", description = "재료 컬렉션 관리 API")
public class IngredientController {
  private final IngredientCollectionUseCase ingredientCollectionUseCase;

  @RequestMapping(method = RequestMethod.POST, value = "/createCollection", produces = { "application/json" })
  @Operation(summary = "재료 컬렉션 생성", description = "재료 컬렉션을 생성합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "재료 컬렉션 생성 성공", content = @Content(schema = @Schema(implementation = CreateCollectionResDto.class))),
      @ApiResponse(responseCode = "400", description = "잘못된 요청 (유효성 검증 실패 등)", content = @Content),
      @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
  })
  public ResponseEntity<CreateCollectionResDto> createCollection(@Valid @RequestBody CreateCollectionReqDto req) {
    CreateCollectionResDto res = ingredientCollectionUseCase.createCollection(req);
    return ResponseEntity.ok(res);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/selectCollectionById", produces = { "application/json" })
  @Operation(summary = "재료 컬렉션 조회", description = "컬렉션 ID로 재료 컬렉션 정보를 조회합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "재료 컬렉션 조회 성공", content = @Content(schema = @Schema(implementation = SelectCollectionResDto.class))),
      @ApiResponse(responseCode = "400", description = "잘못된 요청 (유효하지 않은 ID 등)", content = @Content),
      @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
  })
  public ResponseEntity<SelectCollectionResDto> selectCollectionById(
      @Parameter(description = "컬렉션 ID", required = true, example = "1") @RequestParam("collectionId") Long collectionId) {
    SelectCollectionResDto res = ingredientCollectionUseCase.selectCollectionById(collectionId);
    return ResponseEntity.ok(res);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/updateCollection", produces = { "application/json" })
  @Operation(summary = "재료 컬렉션 수정", description = "재료 컬렉션 정보를 수정합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "재료 컬렉션 수정 성공", content = @Content(schema = @Schema(implementation = UpdateCollectionResDto.class))),
      @ApiResponse(responseCode = "400", description = "잘못된 요청 (컬렉션 없음, 유효성 검증 실패 등)", content = @Content),
      @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
  })
  public ResponseEntity<UpdateCollectionResDto> updateCollection(@Valid @RequestBody UpdateCollectionReqDto req) {
    UpdateCollectionResDto res = ingredientCollectionUseCase.updateCollection(req);
    return ResponseEntity.ok(res);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/deleteCollection", produces = { "application/json" })
  @Operation(summary = "재료 컬렉션 삭제", description = "재료 컬렉션을 삭제합니다 (포함된 아이템도 함께 삭제됩니다).")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "재료 컬렉션 삭제 성공", content = @Content(schema = @Schema(implementation = DeleteCollectionResDto.class))),
      @ApiResponse(responseCode = "400", description = "잘못된 요청 (컬렉션 없음 등)", content = @Content),
      @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
  })
  public ResponseEntity<DeleteCollectionResDto> deleteCollection(
      @Parameter(description = "컬렉션 ID", required = true, example = "1") @RequestParam("collectionId") Long collectionId) {
    DeleteCollectionResDto res = ingredientCollectionUseCase.deleteCollection(collectionId);
    return ResponseEntity.ok(res);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/addCollectionItem", produces = { "application/json" })
  @Operation(summary = "컬렉션에 재료 추가", description = "컬렉션에 재료를 추가합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "컬렉션에 재료 추가 성공", content = @Content(schema = @Schema(implementation = AddCollectionItemResDto.class))),
      @ApiResponse(responseCode = "400", description = "잘못된 요청 (컬렉션 없음, 재료 없음, 이미 포함된 재료, 유효성 검증 실패 등)", content = @Content),
      @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
  })
  public ResponseEntity<AddCollectionItemResDto> addCollectionItem(@Valid @RequestBody AddCollectionItemReqDto req) {
    AddCollectionItemResDto res = ingredientCollectionUseCase.addCollectionItem(req);
    return ResponseEntity.ok(res);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/updateCollectionItemOrder", produces = { "application/json" })
  @Operation(summary = "컬렉션 아이템 순서 수정", description = "컬렉션 아이템의 순서를 수정합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "컬렉션 아이템 순서 수정 성공", content = @Content(schema = @Schema(implementation = UpdateCollectionItemOrderResDto.class))),
      @ApiResponse(responseCode = "400", description = "잘못된 요청 (아이템 없음, 유효성 검증 실패 등)", content = @Content),
      @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
  })
  public ResponseEntity<UpdateCollectionItemOrderResDto> updateCollectionItemOrder(
      @Valid @RequestBody UpdateCollectionItemOrderReqDto req) {
    UpdateCollectionItemOrderResDto res = ingredientCollectionUseCase.updateCollectionItemOrder(req);
    return ResponseEntity.ok(res);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/deleteCollectionItem", produces = { "application/json" })
  @Operation(summary = "컬렉션 아이템 삭제", description = "컬렉션에서 재료를 제거합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "컬렉션 아이템 삭제 성공", content = @Content(schema = @Schema(implementation = DeleteCollectionItemResDto.class))),
      @ApiResponse(responseCode = "400", description = "잘못된 요청 (아이템 없음 등)", content = @Content),
      @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
  })
  public ResponseEntity<DeleteCollectionItemResDto> deleteCollectionItem(
      @Parameter(description = "아이템 ID", required = true, example = "1") @RequestParam("collectionItemId") Long collectionItemId) {
    DeleteCollectionItemResDto res = ingredientCollectionUseCase.deleteCollectionItem(collectionItemId);
    return ResponseEntity.ok(res);
  }
}
