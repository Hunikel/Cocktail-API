package com.santo.cocktail.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "cocktails")
public class Cocktail {

    @Indexed(unique = true)
    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Category is required")
    private String category;

    @NotEmpty(message = "Short description is required")
    private String shortDescription;

    private String longDescription;

    private String imageUrl;

    @NotNull(message = "Measurements are required")
    private List<CocktailMeasurement> measurements;
}
