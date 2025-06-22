package com.galmv.application.useCases.registerRecipe;

import jakarta.validation.constraints.NotBlank;

public record RegisterRecipeRequest(
    @NotBlank(message = "Title is required") String title,
    String description,
    @NotBlank(message = "Ingredients are required") String ingredients,
    @NotBlank(message = "Instructions are required") String instructions) {
}
