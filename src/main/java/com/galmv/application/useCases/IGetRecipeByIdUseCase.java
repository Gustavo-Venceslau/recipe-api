package com.galmv.application.useCases;

import java.util.UUID;

import com.galmv.domain.entites.Recipe;

public interface IGetRecipeByIdUseCase {
  public Recipe execute(UUID id);
}
