package com.staticvoid.menuandordersservice.dto.requests;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class IngredientRequestDto {

    @NotBlank(message = "Ingredient name is required")
    private String name;

    @NotNull(message = "Extra price is required")
    @DecimalMin(value = "0.00", message = "Extra price cannot be negative")
    private BigDecimal extraPrice;

    private boolean available = true;

    public IngredientRequestDto() {
    }

    public String getName() {
        return name;
    }

    public BigDecimal getExtraPrice() {
        return extraPrice;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExtraPrice(BigDecimal extraPrice) {
        this.extraPrice = extraPrice;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}