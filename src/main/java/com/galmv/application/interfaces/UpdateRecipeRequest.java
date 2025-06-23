package com.galmv.application.interfaces;

import java.util.List;

public record UpdateRecipeRequest(
    String title,
    String description,
    List<String> ingridients,
    Integer servings,
    Boolean vegetarian,
    String instructions) {
}
