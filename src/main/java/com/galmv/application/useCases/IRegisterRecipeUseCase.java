package com.galmv.application.useCases;

import com.galmv.application.interfaces.RegisterRecipeRequest;
import com.galmv.domain.entites.Recipe;

public interface IRegisterRecipeUseCase {
 public Recipe execute(RegisterRecipeRequest recipe);
}
