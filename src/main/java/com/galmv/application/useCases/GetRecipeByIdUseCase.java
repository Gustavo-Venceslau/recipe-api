package com.galmv.application.useCases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.galmv.domain.entites.Recipe;
import com.galmv.domain.exceptions.ResourceNotFoundException;
import com.galmv.domain.repositories.RecipeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetRecipeByIdUseCase {
  private final RecipeRepository recipeRepository;

  public Recipe execute(UUID recipeId) {
    Optional<Recipe> recipeFound = recipeRepository.findById(recipeId);

    if (recipeFound.isEmpty()) {
      throw new ResourceNotFoundException("Recipe not found by id");
    }

    return recipeFound.get();
  }
}
