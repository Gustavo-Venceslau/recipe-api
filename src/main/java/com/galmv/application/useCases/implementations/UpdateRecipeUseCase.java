package com.galmv.application.useCases.implementations;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.galmv.application.interfaces.UpdateRecipeRequest;
import com.galmv.application.useCases.IUpdateRecipeUseCase;
import com.galmv.domain.entites.Recipe;
import com.galmv.domain.exceptions.ResourceNotFoundException;
import com.galmv.domain.repositories.RecipeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateRecipeUseCase implements IUpdateRecipeUseCase{
  private final RecipeRepository recipeRepository;

  public Recipe execute(UUID recipeId, UpdateRecipeRequest request) {
    Optional<Recipe> recipeFound = recipeRepository.findById(recipeId);

    if (recipeFound.isEmpty()) {
      throw new ResourceNotFoundException("Recipe not found to updated");
    }

    Recipe recipeToUpdate = recipeFound.get();

    updateRecipeData(recipeToUpdate, request);

    recipeRepository.save(recipeToUpdate);

    return recipeToUpdate;
  }

  private void updateRecipeData(Recipe recipe, UpdateRecipeRequest data) {
    recipe.setTitle(data.title());
    recipe.setDescription(data.description());
    recipe.setIngredients(data.ingridients());
    recipe.setInstructions(data.instructions());
  }
}
