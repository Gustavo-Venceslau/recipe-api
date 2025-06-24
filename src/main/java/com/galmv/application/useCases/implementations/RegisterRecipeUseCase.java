package com.galmv.application.useCases.implementations;

import org.springframework.stereotype.Service;

import com.galmv.application.interfaces.RegisterRecipeRequest;
import com.galmv.application.useCases.IRegisterRecipeUseCase;
import com.galmv.domain.entites.Recipe;
import com.galmv.domain.repositories.RecipeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegisterRecipeUseCase implements IRegisterRecipeUseCase{
  private final RecipeRepository recipeRepository;

  public Recipe execute(RegisterRecipeRequest recipe) {
    Recipe recipeEntity = Recipe.builder()
        .title(recipe.title())
        .description(recipe.description())
        .ingredients(recipe.ingredients())
        .servings(recipe.servings())
        .vegetarian(recipe.vegetarian())
        .instructions(recipe.instructions())
        .build();

    Recipe savedRecipe = recipeRepository.save(recipeEntity);

    return savedRecipe;
  }
}
