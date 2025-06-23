package com.galmv.integration.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.galmv.adapter.controllers.DeleteRecipeController;
import com.galmv.application.useCases.DeleteRecipeUseCase;
import com.galmv.domain.exceptions.ResourceNotFoundException;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = {DeleteRecipeController.class})
@AutoConfigureMockMvc
@Slf4j
public class DeleteRecipeControllerTest {
    static String DELETE_RECIPE_API = "/recipes/";

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    DeleteRecipeUseCase deleteRecipeUseCase;

    @Test
    @DisplayName("Given a valid recipe id, when deleting, it must return 204 No Content")
    public void successfulRecipeDeletion() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(MockMvcRequestBuilders.delete(DELETE_RECIPE_API + id))
            .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Given a non-existing recipe id, when deleting, it must return 404 Not Found")
    public void recipeNotFoundOnDelete() throws Exception {
        UUID id = UUID.randomUUID();
        org.mockito.Mockito.doThrow(new ResourceNotFoundException("Recipe not found to delete"))
            .when(deleteRecipeUseCase).execute(any());
        mockMvc.perform(MockMvcRequestBuilders.delete(DELETE_RECIPE_API + id))
            .andExpect(status().isNotFound());
    }
}
