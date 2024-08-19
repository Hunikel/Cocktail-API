package com.santo.cocktail.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "quantities")
public class Quantity {

    private static final String INCOMPATIBLE_UNITS = "Incompatible units";
    private int value;
    private String unit;

    public Quantity add(Quantity q) {
        if (unit.equals(q.unit)) {
            return new Quantity(value + q.value, unit);
        } else {
            throw new IllegalArgumentException(INCOMPATIBLE_UNITS);
        }
    }

    public Quantity subtract(Quantity q) {
        if (unit.equals(q.unit)) {
            return new Quantity(value - q.value, unit);
        } else {
            throw new IllegalArgumentException(INCOMPATIBLE_UNITS);
        }
    }

    public Quantity multiply(int n) {
        return new Quantity(value * n, unit);
    }

    public Quantity divide(int n) {
        return new Quantity(value / n, unit);
    }

    public Quantity convert(String newUnit) {
        if (unit.equals("cl") && newUnit.equals("ml")) {
            return new Quantity(value * 10, newUnit);
        } else if (unit.equals("ml") && newUnit.equals("cl")) {
            return new Quantity(value / 10, newUnit);
        } else {
            throw new IllegalArgumentException(INCOMPATIBLE_UNITS);
        }
    }

    public boolean isGreaterThan(Quantity q) {
        if (unit.equals(q.unit)) {
            return value > q.value;
        } else {
            throw new IllegalArgumentException(INCOMPATIBLE_UNITS);
        }
    }

    public boolean isLessThan(Quantity q) {
        if (unit.equals(q.unit)) {
            return value < q.value;
        } else {
            throw new IllegalArgumentException(INCOMPATIBLE_UNITS);
        }
    }

    public boolean isEqualTo(Quantity q) {
        if (unit.equals(q.unit)) {
            return value == q.value;
        } else {
            throw new IllegalArgumentException(INCOMPATIBLE_UNITS);
        }
    }

    public boolean isGreaterThanOrEqualTo(Quantity q) {
        return unit.equals(q.unit) && value >= q.value;
    }
}
