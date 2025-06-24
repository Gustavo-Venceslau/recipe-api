package com.galmv.application.useCases;

import java.util.Set;

import com.galmv.application.interfaces.SearchRecipesFilter;
import com.galmv.domain.entites.Recipe;

public interface ISearchRecipeUseCase {
  public Set<Recipe> execute(SearchRecipesFilter filter);
}
