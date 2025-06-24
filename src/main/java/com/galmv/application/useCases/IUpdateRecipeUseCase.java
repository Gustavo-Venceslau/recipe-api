package com.galmv.application.useCases;

import java.util.UUID;

import com.galmv.application.interfaces.UpdateRecipeRequest;
import com.galmv.domain.entites.Recipe;

public interface IUpdateRecipeUseCase {
  public Recipe execute(UUID id, UpdateRecipeRequest request);
}
