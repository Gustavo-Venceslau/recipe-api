package com.galmv.application.interfaces;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public record DeleteRecipeRequest(
    @NotBlank(message = "id filed is required to delete") UUID id) {
}
