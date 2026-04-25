package com.staticvoid.menuandordersservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponseDto {

    private Long id;
    private Long menuItemId;
    private String menuItemName;
    private BigDecimal basePriceAtOrderTime;
    private Integer quantity;
    private BigDecimal itemTotalPrice;
    private List<OrderItemIngredientResponseDto> ingredientChanges = new ArrayList<>();

    public void setIngredientChanges(List<OrderItemIngredientResponseDto> ingredientChanges) {
        this.ingredientChanges = ingredientChanges == null ? new ArrayList<>() : ingredientChanges;
    }
}