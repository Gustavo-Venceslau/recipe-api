package com.galmv.unit.useCases;

import static org.assertj.core.api.Assertions.assertThat;
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

import com.galmv.application.interfaces.UpdateRecipeRequest;
import com.galmv.application.useCases.UpdateRecipeUseCase;
import com.galmv.domain.entites.Recipe;
import com.galmv.domain.exceptions.ResourceNotFoundException;
import com.galmv.domain.repositories.RecipeRepository;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UpdateRecipeUseCaseTest {

  UpdateRecipeUseCase updateRecipeUseCase;

  @Mock
  RecipeRepository recipeRepository;

  @BeforeEach
  public void setup() {
    updateRecipeUseCase = new UpdateRecipeUseCase(recipeRepository);
  }

  @Test
  @DisplayName("given existing recipe id and valid data when update is called then recipe is updated")
  public void successfulUpdateRecipe() {
    UUID id = UUID.randomUUID();
    Recipe recipe = createValidRecipe(id);
    UpdateRecipeRequest request = new UpdateRecipeRequest(
        "Updated Cake",
        "Even more delicious",
        Arrays.asList("Flour, Sugar, Cocoa, Eggs, Butter, Vanilla"),
        1,
        null,
        "Mix and bake for 35 minutes at 180C");
    when(recipeRepository.findById(id)).thenReturn(Optional.of(recipe));
    when(recipeRepository.save(recipe)).thenReturn(recipe);

    Recipe updated = updateRecipeUseCase.execute(id, request);

    assertThat(updated.getTitle()).isEqualTo(request.title());
    assertThat(updated.getDescription()).isEqualTo(request.description());
    assertThat(updated.getIngredients()).isEqualTo(request.ingridients());
    assertThat(updated.getInstructions()).isEqualTo(request.instructions());
    verify(recipeRepository).save(recipe);
  }

  @Test
  @DisplayName("given non-existing recipe id when update is called then throw ResourceNotFoundException")
  public void updateRecipeNotFound() {
    UUID id = UUID.randomUUID();
    UpdateRecipeRequest request = new UpdateRecipeRequest(
        "Updated Cake",
        "Even more delicious",
        Arrays.asList("Flour, Sugar, Cocoa, Eggs, Butter, Vanilla"),
        1,
        null,
        "Mix and bake for 35 minutes at 180C");
    when(recipeRepository.findById(id)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> updateRecipeUseCase.execute(id, request))
        .isInstanceOf(ResourceNotFoundException.class)
        .hasMessage("Recipe not found to updated");
  }

  private Recipe createValidRecipe(UUID id) {
    return Recipe.builder()
        .id(id)
        .title("Chocolate Cake")
        .description("Delicious and moist chocolate cake")
        .ingredients(Arrays.asList("Flour, Sugar, Cocoa, Eggs, Butter"))
        .instructions("Mix ingredients and bake for 30 minutes at 180C")
        .build();
  }
}
