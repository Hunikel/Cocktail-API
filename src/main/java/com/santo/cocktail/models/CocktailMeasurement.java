package com.santo.cocktail.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "cocktail_measurements")
public class CocktailMeasurement {

    private Ingredient ingredient;
    private Quantity quantity;

}
