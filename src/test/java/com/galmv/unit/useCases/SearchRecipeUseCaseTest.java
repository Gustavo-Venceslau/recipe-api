package com.galmv.unit.useCases;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.galmv.application.interfaces.SearchRecipesFilter;
import com.galmv.application.useCases.ISearchRecipeUseCase;
import com.galmv.application.useCases.implementations.SearchRecipeUseCase;
import com.galmv.domain.entites.Recipe;
import com.galmv.domain.repositories.RecipeRepository;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class SearchRecipeUseCaseTest {

  ISearchRecipeUseCase searchRecipeUseCase;

  @Mock
  RecipeRepository recipeRepository;

  private List<Recipe> allRecipes;

  @BeforeEach
  public void setup() {
    searchRecipeUseCase = new SearchRecipeUseCase(recipeRepository);
    allRecipes = Arrays.asList(
      Recipe.builder()
        .title("Chocolate Cake")
        .description("Delicious and moist chocolate cake")
        .servings(1)
        .vegetarian(false)
        .ingredients(Arrays.asList("Flour", "Sugar", "Cocoa", "Eggs", "Butter"))
        .instructions("Mix ingredients and bake for 30 minutes at 180C")
        .build(),
      Recipe.builder()
        .title("Vegetarian Lasagna")
        .description("Hearty lasagna with layers of vegetables and cheese")
        .servings(6)
        .vegetarian(true)
        .ingredients(Arrays.asList("Lasagna Noodles", "Tomato Sauce", "Zucchini", "Spinach", "Ricotta Cheese", "Mozzarella"))
        .instructions("Layer ingredients and bake for 45 minutes at 180C")
        .build()
    );
  }

  @Test
  @DisplayName("should return all recipes when no filters are applied")
  public void testNoFilters() {
    Mockito.when(recipeRepository.findAll(Mockito.<Specification<Recipe>>any())).thenReturn(allRecipes);
    SearchRecipesFilter filter = new SearchRecipesFilter(null, null, null, null, null);
    Set<Recipe> result = searchRecipeUseCase.execute(filter);
    assertThat(result).containsExactlyInAnyOrderElementsOf(allRecipes);
  }

  @Test
  @DisplayName("should filter by vegetarian")
  public void testVegetarianFilter() {
    Mockito.when(recipeRepository.findAll(Mockito.<Specification<Recipe>>any())).thenReturn(Collections.singletonList(allRecipes.get(1)));
    SearchRecipesFilter filter = new SearchRecipesFilter(true, null, null, null, null);
    Set<Recipe> result = searchRecipeUseCase.execute(filter);
    assertThat(result).containsExactly(allRecipes.get(1));
  }

  @Test
  @DisplayName("should filter by servings")
  public void testServingsFilter() {
    Mockito.when(recipeRepository.findAll(Mockito.<Specification<Recipe>>any())).thenReturn(Collections.singletonList(allRecipes.get(0)));
    SearchRecipesFilter filter = new SearchRecipesFilter(null, 1, null, null, null);
    Set<Recipe> result = searchRecipeUseCase.execute(filter);
    assertThat(result).containsExactly(allRecipes.get(0));
  }

  @Test
  @DisplayName("should filter by included ingredient")
  public void testIncludeIngredients() {
    Mockito.when(recipeRepository.findAll(Mockito.<Specification<Recipe>>any())).thenReturn(Collections.singletonList(allRecipes.get(1)));
    SearchRecipesFilter filter = new SearchRecipesFilter(null, null, Arrays.asList("Zucchini"), null, null);
    Set<Recipe> result = searchRecipeUseCase.execute(filter);
    assertThat(result).containsExactly(allRecipes.get(1));
  }

  @Test
  @DisplayName("should filter by excluded ingredient")
  public void testExcludeIngredients() {
    Mockito.when(recipeRepository.findAll(Mockito.<Specification<Recipe>>any())).thenReturn(Collections.singletonList(allRecipes.get(1)));
    SearchRecipesFilter filter = new SearchRecipesFilter(null, null, null, Arrays.asList("Cocoa"), null);
    Set<Recipe> result = searchRecipeUseCase.execute(filter);
    assertThat(result).containsExactly(allRecipes.get(1));
  }

  @Test
  @DisplayName("should filter by instruction content")
  public void testInstructionContent() {
    Mockito.when(recipeRepository.findAll(Mockito.<Specification<Recipe>>any())).thenReturn(Collections.singletonList(allRecipes.get(0)));
    SearchRecipesFilter filter = new SearchRecipesFilter(null, null, null, null, "bake for 30 minutes");
    Set<Recipe> result = searchRecipeUseCase.execute(filter);
    assertThat(result).containsExactly(allRecipes.get(0));
  }

  @Test
  @DisplayName("should filter by multiple criteria")
  public void testCombinedFilters() {
    Mockito.when(recipeRepository.findAll(Mockito.<Specification<Recipe>>any())).thenReturn(Collections.singletonList(allRecipes.get(1)));
    SearchRecipesFilter filter = new SearchRecipesFilter(true, 6, Arrays.asList("Spinach"), null, "Layer ingredients");
    Set<Recipe> result = searchRecipeUseCase.execute(filter);
    assertThat(result).containsExactly(allRecipes.get(1));
  }
}
