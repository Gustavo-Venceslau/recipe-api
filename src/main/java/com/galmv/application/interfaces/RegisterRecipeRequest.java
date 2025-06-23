package com.galmv.application.interfaces;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRecipeRequest(
    @NotBlank(message = "Title is required") String title,
    String description,
    @NotNull(message = "Servings is required") Integer servings,
    Boolean vegetarian,
    @NotBlank(message = "Ingredients are required") List<String> ingredients,
    @NotBlank(message = "Instructions are required") String instructions) {
}
