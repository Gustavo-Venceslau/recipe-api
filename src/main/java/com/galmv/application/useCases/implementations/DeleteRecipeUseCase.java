package com.galmv.application.useCases.implementations;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.galmv.application.interfaces.DeleteRecipeRequest;
import com.galmv.application.useCases.IDeleteRecipeUseCase;
import com.galmv.domain.entites.Recipe;
import com.galmv.domain.exceptions.ResourceNotFoundException;
import com.galmv.domain.repositories.RecipeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteRecipeUseCase implements IDeleteRecipeUseCase{
  private final RecipeRepository recipeRepository;

  public void execute(DeleteRecipeRequest request) {
    Optional<Recipe> recipe = recipeRepository.findById(request.id());

    if (recipe.isEmpty()) {
      throw new ResourceNotFoundException("Recipe not found to delete");
    }

    recipeRepository.delete(recipe.get());
  }
}
