package com.gustoexpedition.ingredient.adapter.in;

import com.gustoexpedition.ingredient.adapter.in.dto.CreateIngredientReqDto;
import com.gustoexpedition.ingredient.adapter.in.dto.CreateIngredientResDto;
import com.gustoexpedition.ingredient.application.port.in.IngredientAdminUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/ingredient")
@RequiredArgsConstructor
@Tag(name = "재료 관리", description = "재료 생성 및 조회 API")
public class IngredientAdminController {
    private final IngredientAdminUseCase ingredientAdminUseCase;

    @PostMapping("/create")
    @Operation(
            summary = "재료 생성",
            description = "새로운 재료를 생성합니다. 재료 이름, 설명, 썸네일 URL 등을 설정할 수 있습니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "재료 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 (중복된 이름, 유효성 검증 실패 등)"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<CreateIngredientResDto> create(@RequestBody CreateIngredientReqDto req) {
        CreateIngredientResDto res = ingredientAdminUseCase.createIngredient(req);
        return ResponseEntity.ok(res);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/get",
            produces = { "application/json" }
    )
    public ResponseEntity<String> get(@RequestParam("id") Long id) {
        return ResponseEntity.ok("hh");
    }
}
