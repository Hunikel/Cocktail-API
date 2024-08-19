package com.santo.cocktail.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@EqualsAndHashCode
@Document(collection="cocktails")
public class Cocktail {

    @Indexed(unique = true)
    private String name;
    private String category;
    private String shortDescription;
    private String longDescription;
    private String imageUrl;
    private List<CocktailMeasurement> measurements;

}
