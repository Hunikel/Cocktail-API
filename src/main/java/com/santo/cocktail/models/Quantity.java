package com.santo.cocktail.models;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class Quantity {

    public enum Unit {
        ML, CL
    }

    private static final String INCOMPATIBLE_UNITS = "Incompatible units";

    @Positive(message = "Value must be positive")
    private int value;

    @NotNull(message = "Unit is required")
    @Enumerated(EnumType.STRING)
    private Unit unit;

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
        if (n == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        return new Quantity(value / n, unit);
    }

    public Quantity convert(Unit newUnit) {
        if (unit == Unit.CL && newUnit == Unit.ML) {
            return new Quantity(value * 10, newUnit);
        } else if (unit == Unit.ML && newUnit == Unit.CL) {
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
