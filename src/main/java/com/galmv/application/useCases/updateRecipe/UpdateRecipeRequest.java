package com.galmv.application.useCases.updateRecipe;

public record UpdateRecipeRequest(
    String title,
    String description,
    String ingridients,
    String instructions) {
}
