package com.galmv.application.interfaces;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRecipeRequest(
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must be at most 255 characters")
    String title,
    @Size(max = 1000, message = "Description must be at most 1000 characters")
    String description,
    @NotNull(message = "Servings is required") Integer servings,
    Boolean vegetarian,
    @NotNull(message = "Ingredients are required")
    @Size(min = 1, message = "At least one ingredient is required")
    List<String> ingredients,
    @NotBlank(message = "Instructions are required")
    @Size(max = 2000, message = "Instructions must be at most 2000 characters")
    String instructions) {
}
