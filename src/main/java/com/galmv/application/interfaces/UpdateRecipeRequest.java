package com.galmv.application.interfaces;

public record UpdateRecipeRequest(
    String title,
    String description,
    String ingridients,
    String instructions) {
}
