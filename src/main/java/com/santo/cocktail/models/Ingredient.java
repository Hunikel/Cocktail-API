package com.santo.cocktail.models;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "ingredients")
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {

    @Indexed(unique = true)
    @NotEmpty(message = "Ingredient name is required")
    private String name;
}
