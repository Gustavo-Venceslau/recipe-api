package com.galmv.adapter.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.galmv.application.interfaces.DeleteRecipeRequest;
import com.galmv.application.useCases.implementations.DeleteRecipeUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/recipes/{id}")
@RequiredArgsConstructor
@Slf4j
public class DeleteRecipeController {
  private final DeleteRecipeUseCase deleteRecipeUseCase;

  @DeleteMapping
  public ResponseEntity<Void> handle(@PathVariable("id") UUID id) {
    deleteRecipeUseCase.execute(new DeleteRecipeRequest(id));

    return ResponseEntity.noContent().build();
  }
}
