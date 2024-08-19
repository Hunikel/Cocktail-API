package com.santo.cocktail.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode
@Document(collection = "ingredients")
public class Ingredient {

    @Indexed(unique = true)
    private String name;

}
