package com.gustoexpedition.recipe.adapter.in.dto;

import com.gustoexpedition.common.annotation.XssSafe;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName : com.gustoexpedition.recipe.adapter.in.dto
 * fileName : CreateRecipeReqDto
 * author : fddsg
 * date : 2026-01-16
 * description : 레시피 생성 요청 DTO
 */
@Getter
@Setter
@Schema(description = "레시피 생성 요청")
public class CreateRecipeReqDto {

  @XssSafe
  @NotBlank(message = "RECIPE001")
  @Size(max = 120, message = "RECIPE004")
  @Schema(description = "레시피 제목", example = "토마토 파스타", requiredMode = Schema.RequiredMode.REQUIRED)
  private String title;

  @XssSafe
  @Size(max = 500, message = "RECIPE006")
  @Schema(description = "레시피 출처", example = "https://example.com/recipe/tomato-pasta", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  private String source;
}
