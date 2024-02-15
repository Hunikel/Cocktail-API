package com.santo.cocktail.repository;

import com.santo.cocktail.models.Ingredient;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface IngredientRepository extends ReactiveCrudRepository<Ingredient, String> {

    Flux<Ingredient> findByNameStartingWith(String name);
}
