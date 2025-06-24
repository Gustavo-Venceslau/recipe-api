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
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galmv.adapter.controllers.UpdateRecipeController;
import com.galmv.application.interfaces.UpdateRecipeRequest;
import com.galmv.application.useCases.IUpdateRecipeUseCase;
import com.galmv.domain.entites.Recipe;
import com.galmv.domain.exceptions.ResourceNotFoundException;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = {UpdateRecipeController.class})
@AutoConfigureMockMvc
@Slf4j
public class UpdateRecipeControllerTest {
    static String UPDATE_RECIPE_API = "/recipes/";

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    IUpdateRecipeUseCase updateRecipeUseCase;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("Given a valid recipe id and data, when updating, it must return 200 OK and the updated recipe")
    public void successfulRecipeUpdate() throws Exception {
        UUID id = UUID.randomUUID();
        UpdateRecipeRequest request = new UpdateRecipeRequest(
            "Updated Cake",
            "Updated description",
            Arrays.asList("Flour", "Sugar", "Eggs"),
            2,
            true,
            "Mix and bake"
        );
        Recipe updatedRecipe = createRecipeFromRequest(id, request);
        org.mockito.BDDMockito.given(updateRecipeUseCase.execute(any(UUID.class), any(UpdateRecipeRequest.class)))
            .willReturn(updatedRecipe);
        mockMvc.perform(MockMvcRequestBuilders.put(UPDATE_RECIPE_API + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value(request.title()))
            .andExpect(jsonPath("$.description").value(request.description()))
            .andExpect(jsonPath("$.ingredients[0]").value(request.ingridients().get(0)))
            .andExpect(jsonPath("$.servings").value(request.servings()))
            .andExpect(jsonPath("$.vegetarian").value(request.vegetarian()))
            .andExpect(jsonPath("$.instructions").value(request.instructions()));
    }

    @Test
    @DisplayName("Given a non-existing recipe id, when updating, it must return 404 Not Found")
    public void recipeNotFoundOnUpdate() throws Exception {
        UUID id = UUID.randomUUID();
        UpdateRecipeRequest request = new UpdateRecipeRequest(
            "Updated Cake",
            "Updated description",
            Arrays.asList("Flour", "Sugar", "Eggs"),
            2,
            true,
            "Mix and bake"
        );
        org.mockito.BDDMockito.given(updateRecipeUseCase.execute(any(UUID.class), any(UpdateRecipeRequest.class)))
            .willThrow(new ResourceNotFoundException("Recipe not found to updated"));
        mockMvc.perform(MockMvcRequestBuilders.put(UPDATE_RECIPE_API + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isNotFound());
    }

    private Recipe createRecipeFromRequest(UUID id, UpdateRecipeRequest request) {
        return Recipe.builder()
            .id(id)
            .title(request.title())
            .description(request.description())
            .ingredients(request.ingridients())
            .servings(request.servings())
            .vegetarian(request.vegetarian())
            .instructions(request.instructions())
            .build();
    }
}
