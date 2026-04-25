package com.staticvoid.menuandordersservice.dto.requests;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class IngredientRequestDto {

    @NotBlank(message = "Ingredient name is required")
    private String name;

    @NotNull(message = "Extra price is required")
    @DecimalMin(value = "0.00", message = "Extra price cannot be negative")
    @Digits(integer = 8, fraction = 2, message = "Extra price must have up to 8 digits and 2 decimal places")
    private BigDecimal extraPrice;

    private Boolean available;
}