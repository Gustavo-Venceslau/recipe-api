package com.galmv.unit.useCases;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.galmv.application.interfaces.DeleteRecipeRequest;
import com.galmv.application.useCases.IDeleteRecipeUseCase;
import com.galmv.application.useCases.implementations.DeleteRecipeUseCase;
import com.galmv.domain.entites.Recipe;
import com.galmv.domain.exceptions.ResourceNotFoundException;
import com.galmv.domain.repositories.RecipeRepository;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class DeleteRecipeUseCaseTest {

  IDeleteRecipeUseCase deleteRecipeUseCase;

  @Mock
  RecipeRepository recipeRepository;

  @BeforeEach
  public void setup() {
    deleteRecipeUseCase = new DeleteRecipeUseCase(recipeRepository);
  }

  @Test
  @DisplayName("given existing recipe id when delete is called then recipe is deleted")
  public void successfulDeleteRecipe() {
    UUID id = UUID.randomUUID();
    Recipe recipe = createValidRecipe(id);
    when(recipeRepository.findById(id)).thenReturn(Optional.of(recipe));

    DeleteRecipeRequest request = new DeleteRecipeRequest(id);
    deleteRecipeUseCase.execute(request);

    verify(recipeRepository).delete(recipe);
  }

  @Test
  @DisplayName("given non-existing recipe id when delete is called then throw ResourceNotFoundException")
  public void deleteRecipeNotFound() {
    UUID id = UUID.randomUUID();
    when(recipeRepository.findById(id)).thenReturn(Optional.empty());

    DeleteRecipeRequest request = new DeleteRecipeRequest(id);

    assertThatThrownBy(() -> deleteRecipeUseCase.execute(request))
        .isInstanceOf(ResourceNotFoundException.class)
        .hasMessage("Recipe not found to delete");
  }

  private Recipe createValidRecipe(UUID id) {
    return Recipe.builder()
        .id(id)
        .title("Chocolate Cake")
        .description("Delicious and moist chocolate cake")
        .servings(1)
        .ingredients(Arrays.asList("Flour", "Sugar", "Cocoa", "Eggs", "Butter"))
        .instructions("Mix ingredients and bake for 30 minutes at 180C")
        .build();
  }
}
