package com.galmv.domain.entites;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recipes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Recipe extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  @Column(name = "title", nullable = false, length = 255)
  private String title;
  @Column(name = "description", length = 1000)
  private String description;
  @Column(name = "ingredients", nullable = false)
  @ElementCollection(fetch = FetchType.EAGER)
  private List<String> ingredients;
  @Column(name = "instructions", nullable = false, length = 2000)
  private String instructions;
  @Column(name = "servings", nullable = false)
  private Integer servings;
  @Column(name = "vegetarian", nullable = false)
  @Builder.Default
  private Boolean vegetarian = false;
}
