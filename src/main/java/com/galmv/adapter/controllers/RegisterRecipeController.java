package com.galmv.adapter.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.galmv.application.interfaces.RegisterRecipeRequest;
import com.galmv.application.useCases.RegisterRecipeUseCase;
import com.galmv.domain.entites.Recipe;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/recipes/")
@RequiredArgsConstructor
@Slf4j
public class RegisterRecipeController {

  private final RegisterRecipeUseCase registerRecipeUseCase;

  @PostMapping
  public ResponseEntity<Recipe> handle(@Valid @RequestBody RegisterRecipeRequest request) {
    Recipe newRecipe = registerRecipeUseCase.execute(request);

    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newRecipe.getId()).toUri();

    return ResponseEntity.created(uri).body(newRecipe);
  }
}
