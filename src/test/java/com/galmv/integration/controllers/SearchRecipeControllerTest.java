package com.galmv.integration.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.galmv.adapter.controllers.SearchRecipeController;
import com.galmv.application.interfaces.SearchRecipesFilter;
import com.galmv.application.useCases.ISearchRecipeUseCase;
import com.galmv.domain.entites.Recipe;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = {SearchRecipeController.class})
@AutoConfigureMockMvc
@Slf4j
public class SearchRecipeControllerTest {
  static String SEARCH_RECIPE_API = "/recipes/search";

  @Autowired
  MockMvc mockMvc;

  @MockitoBean
  ISearchRecipeUseCase searchRecipeUseCase;

  @Test
  @DisplayName("Given a recipe search parameters, when trying to search, it must return the recipes associated")
  public void successfulRecipeSearch() throws Exception{
    List<Recipe> savedRecipes = createValidRecipes();

    BDDMockito.given(searchRecipeUseCase
      .execute(Mockito.any(SearchRecipesFilter.class)))
      .willReturn(Set.copyOf(savedRecipes));

    MockHttpServletRequestBuilder request = MockMvcRequestBuilders
      .get(SEARCH_RECIPE_API)
      .param("vegetarian", "false")
      .param("servings", "1")
      .param("includeIngredients", "Flour", "Sugar")
      .param("instruction", "bake");

      mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Chocolate Cake")))
        .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Vanilla Cupcakes")))
        .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Vegetarian Lasagna")));
  }

  @Test
  @DisplayName("When the use case throws an exception, should return 500 Internal Server Error")
  public void searchRecipeInternalServerError() throws Exception {
    BDDMockito.given(searchRecipeUseCase.execute(Mockito.any(SearchRecipesFilter.class)))
      .willThrow(new RuntimeException("Unexpected error"));

    MockHttpServletRequestBuilder request = MockMvcRequestBuilders
      .get(SEARCH_RECIPE_API)
      .param("vegetarian", "false");

    mockMvc.perform(request)
      .andExpect(MockMvcResultMatchers.status().isInternalServerError());
  }

  private static List<Recipe> createValidRecipes() {
    Recipe recipe1 = Recipe.builder()
        .title("Chocolate Cake")
        .description("Delicious and moist chocolate cake")
        .servings(1)
        .ingredients(
          Arrays.asList("Flour", "Sugar", "Cocoa", "Eggs", "Butter"))
        .instructions("Mix ingredients and bake for 30 minutes at 180C")
        .build();

    Recipe recipe2 = Recipe.builder()
        .title("Vanilla Cupcakes")
        .description("Light and fluffy vanilla cupcakes")
        .servings(12)
        .ingredients(
          Arrays.asList("Flour", "Sugar", "Eggs", "Butter", "Vanilla Extract"))
        .instructions("Mix ingredients, pour into cupcake liners, and bake for 20 minutes at 175C")
        .build();

    Recipe recipe3 = Recipe.builder()
        .title("Vegetarian Lasagna")
        .description("Hearty lasagna with layers of vegetables and cheese")
        .servings(6)
        .ingredients(
          Arrays.asList("Lasagna Noodles", "Tomato Sauce", "Zucchini", "Spinach", "Ricotta Cheese", "Mozzarella"))
        .instructions("Layer ingredients and bake for 45 minutes at 180C")
        .build();

    return Arrays.asList(recipe1, recipe2, recipe3);
  }
}
