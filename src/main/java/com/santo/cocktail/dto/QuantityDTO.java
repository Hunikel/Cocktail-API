package com.santo.cocktail.dto;

import com.santo.cocktail.models.Quantity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuantityDTO {

    @Positive(message = "Value must be positive")
    private int value;

    @NotNull(message = "Unit is required")
    private Quantity.Unit unit;
}
