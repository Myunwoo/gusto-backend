package com.gustoexpedition.ingredient.domain;

public class IngredientValidator {
    private IngredientValidator() {}

    public static void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Ingredient name must not be blank");
        }
        if (name.trim().length() > 100) {
            throw new IllegalArgumentException("Ingredient name must be <= 100 chars");
        }
    }
}
