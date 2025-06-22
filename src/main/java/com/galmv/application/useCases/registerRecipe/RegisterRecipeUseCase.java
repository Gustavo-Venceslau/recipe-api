package com.galmv.application.useCases.registerRecipe;

import org.springframework.stereotype.Service;

import com.galmv.domain.entites.Recipe;
import com.galmv.domain.repositories.RecipeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegisterRecipeUseCase {
  private final RecipeRepository recipeRepository;

  public Recipe execute(RegisterRecipeRequest recipe) {
    Recipe recipeEntity = Recipe.builder()
        .title(recipe.title())
        .description(recipe.description())
        .ingredients(recipe.ingredients())
        .instructions(recipe.instructions())
        .build();

    Recipe savedRecipe = recipeRepository.save(recipeEntity);

    return savedRecipe;
  }
}
