package com.santo.cocktail.mapper;

import com.santo.cocktail.dto.CocktailDTO;
import com.santo.cocktail.dto.CocktailMeasurementDTO;
import com.santo.cocktail.dto.IngredientDTO;
import com.santo.cocktail.dto.QuantityDTO;
import com.santo.cocktail.models.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CocktailMapper {

    public CocktailDTO toCocktailDTO(Cocktail cocktail) {
        CocktailDTO dto = new CocktailDTO();
        dto.setName(cocktail.getName());
        dto.setCategory(cocktail.getCategory());
        dto.setShortDescription(cocktail.getShortDescription());
        dto.setLongDescription(cocktail.getLongDescription());
        dto.setImageUrl(cocktail.getImageUrl());
        dto.setMeasurements(toCocktailMeasurementDTOs(cocktail.getMeasurements()));
        return dto;
    }

    public Cocktail toCocktail(CocktailDTO dto) {
        Cocktail cocktail = new Cocktail();
        cocktail.setName(dto.getName());
        cocktail.setCategory(dto.getCategory());
        cocktail.setShortDescription(dto.getShortDescription());
        cocktail.setLongDescription(dto.getLongDescription());
        cocktail.setImageUrl(dto.getImageUrl());
        cocktail.setMeasurements(toCocktailMeasurements(dto.getMeasurements()));
        return cocktail;
    }

    private List<CocktailMeasurementDTO> toCocktailMeasurementDTOs(List<CocktailMeasurement> measurements) {
        return measurements.stream()
                .map(this::toCocktailMeasurementDTO)
                .toList();
    }

    private CocktailMeasurementDTO toCocktailMeasurementDTO(CocktailMeasurement measurement) {
        CocktailMeasurementDTO dto = new CocktailMeasurementDTO();
        dto.setIngredient(new IngredientDTO(measurement.getIngredient().getName()));
        dto.setQuantity(toQuantityDTO(measurement.getQuantity()));
        return dto;
    }

    private QuantityDTO toQuantityDTO(Quantity quantity) {
        QuantityDTO dto = new QuantityDTO();
        dto.setValue(quantity.getValue());
        dto.setUnit(quantity.getUnit());
        return dto;
    }

    private List<CocktailMeasurement> toCocktailMeasurements(List<CocktailMeasurementDTO> measurementDTOs) {
        return measurementDTOs.stream()
                .map(this::toCocktailMeasurement)
                .toList();
    }

    private CocktailMeasurement toCocktailMeasurement(CocktailMeasurementDTO dto) {
        CocktailMeasurement measurement = new CocktailMeasurement();
        measurement.setIngredient(new Ingredient(dto.getIngredient().getName()));
        measurement.setQuantity(toQuantity(dto.getQuantity()));
        return measurement;
    }

    private Quantity toQuantity(QuantityDTO dto) {
        return new Quantity(dto.getValue(), dto.getUnit());
    }
}
