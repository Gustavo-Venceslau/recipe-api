package com.galmv.application.interfaces;

import java.util.List;

public record SearchRecipesFilter(
    Boolean vegetarian,
    Integer servings,
    List<String> includeIngredients,
    List<String> excludeIngredients,
    String instructions) {

}
