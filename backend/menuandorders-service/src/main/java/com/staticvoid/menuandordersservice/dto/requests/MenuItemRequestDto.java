package com.staticvoid.menuandordersservice.dto.requests;

import com.staticvoid.menuandordersservice.model.enums.MenuItemType;
import com.staticvoid.menuandordersservice.model.enums.Season;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class MenuItemRequestDto {

    @NotBlank(message = "Menu item name is required")
    private String name;

    @NotNull(message = "Type is required")
    private MenuItemType type;

    @NotNull(message = "Season is required")
    private Season season;

    private String description;

    @NotNull(message = "Base price is required")
    @DecimalMin(value = "0.00", message = "Base price cannot be negative")
    private BigDecimal basePrice;

    private Boolean available;

    private List<Long> allowedIngredientIds = new ArrayList<>();

    private List<Long> defaultIngredientIds = new ArrayList<>();

    public void setAllowedIngredientIds(List<Long> allowedIngredientIds) {
        this.allowedIngredientIds = allowedIngredientIds == null ? new ArrayList<>() : allowedIngredientIds;
    }

    public void setDefaultIngredientIds(List<Long> defaultIngredientIds) {
        this.defaultIngredientIds = defaultIngredientIds == null ? new ArrayList<>() : defaultIngredientIds;
    }
}