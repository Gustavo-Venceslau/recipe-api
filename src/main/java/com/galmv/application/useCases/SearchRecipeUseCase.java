package com.galmv.application.useCases;

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

    if (filter.vegetarian() != null) {
      spec = spec.and((root, query, cb) ->
        cb.equal(root.get("vegetarian"), filter.vegetarian()));
    }

    if (filter.servings() != null) {
      spec = spec.and((root, query, cb) ->
        cb.equal(root.get("servings"), filter.vegetarian()));
    }

    if (filter.includeIngredients() != null && !filter.includeIngredients().isEmpty()) {
      for (String ingridient : filter.includeIngredients()) {
        spec = spec.and((root, query, cb) ->
          cb.isMember(ingridient, root.get("ingridients")));
      };
    }

    if (filter.excludeIngredients() != null && !filter.excludeIngredients().isEmpty()) {
      for (String ingridient : filter.excludeIngredients()) {
        spec = spec.and((root, query, cb) ->
          cb.not(cb.isMember(ingridient, root.get("ingridients"))));
      };
    }

    if(filter.instructions() != null && !filter.instructions().isBlank()) {
      spec = spec.and((root, query, cb) ->
        cb.like(root.get("instructions"), "%" + filter.instructions() + "%"));
    }

    return Set.copyOf(recipeRepository.findAll(spec));
  }
}
