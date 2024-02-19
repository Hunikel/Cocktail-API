package com.santo.cocktail.services;

import com.santo.cocktail.models.Ingredient;
import com.santo.cocktail.repository.IngredientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.mockito.Mockito.*;

class IngredientServiceTest {
    @Mock
    IngredientRepository ingredientRepository;
    @InjectMocks
    IngredientService ingredientService;

    private List<Ingredient> ingredients;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ingredients = List.of(new Ingredient(), new Ingredient(), new Ingredient(), new Ingredient(), new Ingredient());
        ingredients.forEach(ingredient -> ingredient.setName("name" + ingredients.indexOf(ingredient)));

    }

    @Test
    void testGetAllIngredient() {
        when(ingredientRepository.findAll()).thenReturn(Flux.fromIterable(ingredients));

        Flux<String> result = ingredientService.getAllIngredient();
        Assertions.assertEquals(5, result.count().block());
    }

    @Test
    void testGetAllIngredientsStartingWith() {
        when(ingredientRepository.findByNameStartingWith(anyString())).thenReturn(Flux.fromIterable(ingredients));

        Flux<String> result = ingredientService.getAllIngredientsStartingWith("name");
        Assertions.assertEquals(5, result.count().block());
    }
}
