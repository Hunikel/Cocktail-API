package com.santo.cocktail.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CocktailMeasurementDTO {

    @NotNull(message = "Ingredient is required")
    private IngredientDTO ingredient;

    @NotNull(message = "Quantity is required")
    private QuantityDTO quantity;
}
