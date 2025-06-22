package com.galmv.domain.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.galmv.domain.entites.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, UUID> {

}
