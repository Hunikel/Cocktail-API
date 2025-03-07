package com.santo.cocktail.models;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CocktailMeasurement {

    @NotNull(message = "Ingredient is required")
    private Ingredient ingredient;

    @NotNull(message = "Quantity is required")
    private Quantity quantity;
}
