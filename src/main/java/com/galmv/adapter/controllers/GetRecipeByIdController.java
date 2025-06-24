package com.galmv.adapter.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.galmv.application.useCases.IGetRecipeByIdUseCase;
import com.galmv.domain.entites.Recipe;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/recipes/{id}")
@RequiredArgsConstructor
@Slf4j
public class GetRecipeByIdController {
  private final IGetRecipeByIdUseCase getRecipeByIdUseCase;

  @GetMapping
  public ResponseEntity<Recipe> handle(@PathVariable("id") UUID id) {
    Recipe recipeFound = getRecipeByIdUseCase.execute(id);

    return ResponseEntity.ok().body(recipeFound);
  }
}
