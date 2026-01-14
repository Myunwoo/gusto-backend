package com.gustoexpedition.ingredient.application.port.in;

import com.gustoexpedition.ingredient.adapter.in.dto.CreateIngredientReqDto;
import com.gustoexpedition.ingredient.adapter.in.dto.CreateIngredientResDto;

public interface IngredientAdminUseCase {
    /**
     * methodName : createIngredient
     * author : IM HYUN WOO
     * description :
     *
     * @param req
     * @return create ingredient res dto
     */
    public CreateIngredientResDto createIngredient(CreateIngredientReqDto req);
}
