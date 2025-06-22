package com.galmv.useCases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.galmv.application.useCases.GetRecipeByIdUseCase;
import com.galmv.domain.entites.Recipe;
import com.galmv.domain.exceptions.ResourceNotFoundException;
import com.galmv.domain.repositories.RecipeRepository;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class GetRecipeByIdUseCaseTest {

  GetRecipeByIdUseCase getRecipeByIdUseCase;

  @Mock
  RecipeRepository recipeRepository;

  @BeforeEach
  public void setup() {
    getRecipeByIdUseCase = new GetRecipeByIdUseCase(recipeRepository);
  }

  @Test
  @DisplayName("given existing recipe id when get is called then return recipe")
  public void successfulGetRecipeById() {
    UUID id = UUID.randomUUID();
    Recipe recipe = createValidRecipe(id);
    when(recipeRepository.findById(id)).thenReturn(Optional.of(recipe));

    Recipe found = getRecipeByIdUseCase.execute(id);

    assertThat(found).isNotNull();
    assertThat(found.getId()).isEqualTo(id);
    assertThat(found.getTitle()).isEqualTo(recipe.getTitle());
    assertThat(found.getDescription()).isEqualTo(recipe.getDescription());
    assertThat(found.getIngredients()).isEqualTo(recipe.getIngredients());
    assertThat(found.getInstructions()).isEqualTo(recipe.getInstructions());
  }

  @Test
  @DisplayName("given non-existing recipe id when get is called then throw ResourceNotFoundException")
  public void getRecipeByIdNotFound() {
    UUID id = UUID.randomUUID();
    when(recipeRepository.findById(id)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> getRecipeByIdUseCase.execute(id))
        .isInstanceOf(ResourceNotFoundException.class)
        .hasMessage("Recipe not found by id");
  }

  private Recipe createValidRecipe(UUID id) {
    return Recipe.builder()
        .id(id)
        .title("Chocolate Cake")
        .description("Delicious and moist chocolate cake")
        .ingredients("Flour, Sugar, Cocoa, Eggs, Butter")
        .instructions("Mix ingredients and bake for 30 minutes at 180C")
        .build();
  }
}
