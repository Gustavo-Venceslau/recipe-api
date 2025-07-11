package com.galmv.adapter.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.galmv.application.interfaces.SearchRecipesFilter;
import com.galmv.application.useCases.ISearchRecipeUseCase;
import com.galmv.domain.entites.Recipe;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/recipes/search")
@RequiredArgsConstructor
@Slf4j
public class SearchRecipeController {
  private final ISearchRecipeUseCase searchRecipeUseCase;

  @GetMapping
  public ResponseEntity<Set<Recipe>> handle(
      @RequestParam(required = false) Boolean vegetarian,
      @RequestParam(required = false) Integer servings,
      @RequestParam(required = false) List<String> includeIngredients,
      @RequestParam(required = false) List<String> excludeIngredients,
      @RequestParam(required = false) String instruction
    ) {
      SearchRecipesFilter filter = new SearchRecipesFilter(
          vegetarian,
          servings,
          includeIngredients,
          excludeIngredients,
          instruction);

      Set<Recipe> recipes = searchRecipeUseCase.execute(filter);
      return ResponseEntity.ok(recipes);
    }
}
