package com.staticvoid.menuandordersservice.dto.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CreateOrderItemRequestDto {

    @NotNull(message = "Menu item id is required")
    private Long menuItemId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    private List<Long> addedIngredientIds = new ArrayList<>();

    private List<Long> removedIngredientIds = new ArrayList<>();

    public void setAddedIngredientIds(List<Long> addedIngredientIds) {
        this.addedIngredientIds = addedIngredientIds == null ? new ArrayList<>() : addedIngredientIds;
    }

    public void setRemovedIngredientIds(List<Long> removedIngredientIds) {
        this.removedIngredientIds = removedIngredientIds == null ? new ArrayList<>() : removedIngredientIds;
    }
}