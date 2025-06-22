package com.galmv.recipe.useCases;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.galmv.application.useCases.registerRecipe.RegisterRecipeRequest;
import com.galmv.application.useCases.registerRecipe.RegisterRecipeUseCase;
import com.galmv.domain.entites.Recipe;
import com.galmv.domain.repositories.RecipeRepository;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class RegisterRecipeUseCaseTest {

  RegisterRecipeUseCase registerRecipeUseCase;

  @Mock
  RecipeRepository recipeRepository;

  @BeforeEach
  public void setup() {
    registerRecipeUseCase = new RegisterRecipeUseCase(recipeRepository);
  }

  @Test
  @DisplayName("given new recipe when tried to save must return the new recipe saved")
  public void succesfullSaveRecipe() {
    Recipe recipe = createValidRecipe();

    Mockito.when(recipeRepository.save(recipe)).thenReturn(createValidRecipe());

    RegisterRecipeRequest request = new RegisterRecipeRequest(
        recipe.getTitle(),
        recipe.getDescription(),
        recipe.getIngredients(),
        recipe.getInstructions());

    Recipe recipeSaved = registerRecipeUseCase.execute(request);

    assertThat(recipeSaved.getTitle()).isEqualTo(recipe.getTitle());
    assertThat(recipeSaved.getDescription()).isEqualTo(recipe.getDescription());
    assertThat(recipeSaved.getIngredients()).isEqualTo(recipe.getIngredients());
    assertThat(recipeSaved.getInstructions()).isEqualTo(recipe.getInstructions());
  }

  private Recipe createValidRecipe() {
    return Recipe.builder()
        .title("Chocolate Cake")
        .description("Delicious and moist chocolate cake")
        .ingredients("Flour, Sugar, Cocoa, Eggs, Butter")
        .instructions("Mix ingredients and bake for 30 minutes at 180C")
        .build();
  }
}
