package com.santo.cocktail.services;

import com.santo.cocktail.models.Ingredient;
import com.santo.cocktail.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public Flux<String> getAllIngredient() {
        return ingredientRepository.findAll().map(Ingredient::getName);
    }

    public Flux<String> getAllIngredientsStartingWith(String name) {
        return ingredientRepository.findByNameStartingWith(name).map(Ingredient::getName);
    }
}
