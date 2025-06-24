package com.galmv.integration.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Arrays;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.galmv.adapter.controllers.GetRecipeByIdController;
import com.galmv.application.useCases.implementations.GetRecipeByIdUseCase;
import com.galmv.domain.entites.Recipe;
import com.galmv.domain.exceptions.ResourceNotFoundException;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = {GetRecipeByIdController.class})
@AutoConfigureMockMvc
@Slf4j
public class GetRecipeByIdControllerTest {
    static String GET_RECIPE_API = "/recipe/";

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    GetRecipeByIdUseCase getRecipeByIdUseCase;

    @Test
    @DisplayName("Given a valid recipe id, when searching, it must return the recipe with 200 OK")
    public void successfulGetRecipeById() throws Exception {
        UUID id = UUID.randomUUID();
        Recipe recipe = createRecipe(id);
        org.mockito.BDDMockito.given(getRecipeByIdUseCase.execute(any(UUID.class)))
            .willReturn(recipe);
        mockMvc.perform(MockMvcRequestBuilders.get(GET_RECIPE_API + id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id.toString()))
            .andExpect(jsonPath("$.title").value(recipe.getTitle()))
            .andExpect(jsonPath("$.description").value(recipe.getDescription()))
            .andExpect(jsonPath("$.ingredients[0]").value(recipe.getIngredients().get(0)))
            .andExpect(jsonPath("$.servings").value(recipe.getServings()))
            .andExpect(jsonPath("$.vegetarian").value(recipe.getVegetarian()))
            .andExpect(jsonPath("$.instructions").value(recipe.getInstructions()));
    }

    @Test
    @DisplayName("Given a non-existing recipe id, when searching, it must return 404 Not Found")
    public void recipeNotFoundById() throws Exception {
        UUID id = UUID.randomUUID();
        org.mockito.BDDMockito.given(getRecipeByIdUseCase.execute(any(UUID.class)))
            .willThrow(new ResourceNotFoundException("Recipe not found by id"));
        mockMvc.perform(MockMvcRequestBuilders.get(GET_RECIPE_API + id))
            .andExpect(status().isNotFound());
    }

    private Recipe createRecipe(UUID id) {
        return Recipe.builder()
            .id(id)
            .title("Bolo de Chocolate")
            .description("Bolo delicioso e fofinho de chocolate")
            .ingredients(Arrays.asList("Farinha", "Açúcar", "Cacau", "Ovos", "Manteiga"))
            .servings(8)
            .vegetarian(false)
            .instructions("Misture os ingredientes e asse por 30 minutos a 180C")
            .build();
    }
}
