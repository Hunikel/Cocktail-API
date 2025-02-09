package com.santo.cocktail.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CocktailDTO {

    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Category is required")
    private String category;

    @NotEmpty(message = "Short description is required")
    private String shortDescription;

    private String longDescription;

    private String imageUrl;

    @NotNull(message = "Measurements are required")
    private List<CocktailMeasurementDTO> measurements;
}
