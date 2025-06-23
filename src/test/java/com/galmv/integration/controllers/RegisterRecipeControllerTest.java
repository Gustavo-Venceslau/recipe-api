package com.galmv.integration.controllers;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import com.galmv.adapter.controllers.RegisterRecipeController;
import com.galmv.application.interfaces.RegisterRecipeRequest;
import com.galmv.application.useCases.RegisterRecipeUseCase;
import com.galmv.domain.entites.Recipe;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = {RegisterRecipeController.class})
@AutoConfigureMockMvc
@Slf4j
public class RegisterRecipeControllerTest {
    static String REGISTER_RECIPE_API = "/recipes/";

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    RegisterRecipeUseCase registerRecipeUseCase;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("Given a valid recipe, when registering, it must return the created recipe and 201 status")
    public void successfulRecipeRegistration() throws Exception {
        Recipe savedRecipe = createValidRecipe();
        RegisterRecipeRequest request = new RegisterRecipeRequest(
            savedRecipe.getTitle(),
            savedRecipe.getDescription(),
            savedRecipe.getServings(),
            savedRecipe.getVegetarian(),
            savedRecipe.getIngredients(),
            savedRecipe.getInstructions()
        );

        given(registerRecipeUseCase.execute(any(RegisterRecipeRequest.class)))
            .willReturn(savedRecipe);

        mockMvc.perform(MockMvcRequestBuilders.post(REGISTER_RECIPE_API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.title").value(savedRecipe.getTitle()))
            .andExpect(jsonPath("$.description").value(savedRecipe.getDescription()))
            .andExpect(jsonPath("$.servings").value(savedRecipe.getServings()))
            .andExpect(jsonPath("$.ingredients[0]").value(savedRecipe.getIngredients().get(0)))
            .andExpect(jsonPath("$.instructions").value(savedRecipe.getInstructions()));
    }

    @Test
    @DisplayName("When the use case throws an exception, should return 500 Internal Server Error")
    public void registerRecipeInternalServerError() throws Exception {
        RegisterRecipeRequest request = new RegisterRecipeRequest(
            "Chocolate Cake",
            "Delicious and moist chocolate cake",
            1,
            false,
            Arrays.asList("Flour", "Sugar", "Cocoa", "Eggs", "Butter"),
            "Mix ingredients and bake for 30 minutes at 180C"
        );

        given(registerRecipeUseCase.execute(any(RegisterRecipeRequest.class)))
            .willThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(MockMvcRequestBuilders.post(REGISTER_RECIPE_API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isInternalServerError());
    }

    private static Recipe createValidRecipe() {
        return Recipe.builder()
            .id(UUID.randomUUID())
            .title("Chocolate Cake")
            .description("Delicious and moist chocolate cake")
            .servings(1)
            .vegetarian(false)
            .ingredients(Arrays.asList("Flour", "Sugar", "Cocoa", "Eggs", "Butter"))
            .instructions("Mix ingredients and bake for 30 minutes at 180C")
            .build();
    }
}
