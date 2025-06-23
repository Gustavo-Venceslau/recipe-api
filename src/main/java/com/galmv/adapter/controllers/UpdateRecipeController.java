package com.galmv.adapter.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.galmv.application.interfaces.UpdateRecipeRequest;
import com.galmv.application.useCases.UpdateRecipeUseCase;
import com.galmv.domain.entites.Recipe;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/recipes/{id}")
@RequiredArgsConstructor
@Slf4j
public class UpdateRecipeController {
  private final UpdateRecipeUseCase updateRecipeUseCase;

  @PutMapping
  public ResponseEntity<Recipe> handle(
    @PathVariable("id") UUID id,
    @Valid @RequestBody UpdateRecipeRequest request
  )
  {
    Recipe updatedRecipe = updateRecipeUseCase.execute(id, request);

    return ResponseEntity.ok().body(updatedRecipe);
  }
}
