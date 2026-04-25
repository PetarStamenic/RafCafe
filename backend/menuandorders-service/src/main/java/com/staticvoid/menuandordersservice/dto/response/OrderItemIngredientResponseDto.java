package com.staticvoid.menuandordersservice.dto.response;

import com.staticvoid.menuandordersservice.model.enums.IngredientChangeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemIngredientResponseDto {

    private Long ingredientId;
    private String ingredientName;
    private BigDecimal extraPrice;
    private IngredientChangeType changeType;
}