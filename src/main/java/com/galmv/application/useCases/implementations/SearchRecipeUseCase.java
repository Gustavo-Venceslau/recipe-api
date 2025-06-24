package com.galmv.application.useCases.implementations;

import java.util.Set;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.galmv.application.interfaces.SearchRecipesFilter;
import com.galmv.domain.entites.Recipe;
import com.galmv.domain.repositories.RecipeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchRecipeUseCase {
  private final RecipeRepository recipeRepository;

  public Set<Recipe> execute(SearchRecipesFilter filter) {
    Specification<Recipe> spec = Specification.anyOf();

    spec = addVegetarianFilter(spec, filter);
    spec = addServingsFilter(spec, filter);
    spec = addIncludeIngredientsFilter(spec, filter);
    spec = addExcludeIngredientsFilter(spec, filter);
    spec = addInstructionsFilter(spec, filter);

    return Set.copyOf(recipeRepository.findAll(spec));
  }

  private Specification<Recipe> addVegetarianFilter(Specification<Recipe> spec, SearchRecipesFilter filter) {
    if (filter.vegetarian() != null) {
      return spec.and((root, query, cb) -> cb.equal(root.get("vegetarian"), filter.vegetarian()));
    }
    return spec;
  }

  private Specification<Recipe> addServingsFilter(Specification<Recipe> spec, SearchRecipesFilter filter) {
    if (filter.servings() != null) {
      return spec.and((root, query, cb) -> cb.equal(root.get("servings"), filter.servings()));
    }
    return spec;
  }

  private Specification<Recipe> addIncludeIngredientsFilter(Specification<Recipe> spec, SearchRecipesFilter filter) {
    if (filter.includeIngredients() != null && !filter.includeIngredients().isEmpty()) {
      for (String ingridient : filter.includeIngredients()) {
        spec = spec.and((root, query, cb) ->
          cb.isMember(ingridient, root.get("ingredients")));
      };
    }
    return spec;
  }

  private Specification<Recipe> addExcludeIngredientsFilter(Specification<Recipe> spec, SearchRecipesFilter filter) {
    if (filter.excludeIngredients() != null && !filter.excludeIngredients().isEmpty()) {
      for (String ingridient : filter.excludeIngredients()) {
        spec = spec.and((root, query, cb) ->
          cb.not(cb.isMember(ingridient, root.get("ingredients"))));
      };
    }
    return spec;
  }

  private Specification<Recipe> addInstructionsFilter(Specification<Recipe> spec, SearchRecipesFilter filter) {
    if(filter.instructions() != null && !filter.instructions().isBlank()) {
      return spec.and((root, query, cb) ->
        cb.like(root.get("instructions"), "%" + filter.instructions() + "%"));
    }
    return spec;
  }
}
