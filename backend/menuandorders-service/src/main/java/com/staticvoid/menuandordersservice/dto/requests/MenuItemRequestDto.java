package com.staticvoid.menuandordersservice.dto.requests;

import com.staticvoid.menuandordersservice.model.enums.MenuItemType;
import com.staticvoid.menuandordersservice.model.enums.Season;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    private boolean available = true;

    private List<Long> allowedIngredientIds = new ArrayList<>();

    private List<Long> defaultIngredientIds = new ArrayList<>();

    public MenuItemRequestDto() {
    }

    public String getName() {
        return name;
    }

    public MenuItemType getType() {
        return type;
    }

    public Season getSeason() {
        return season;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public boolean isAvailable() {
        return available;
    }

    public List<Long> getAllowedIngredientIds() {
        return allowedIngredientIds;
    }

    public List<Long> getDefaultIngredientIds() {
        return defaultIngredientIds;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(MenuItemType type) {
        this.type = type;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setAllowedIngredientIds(List<Long> allowedIngredientIds) {
        this.allowedIngredientIds = allowedIngredientIds;
    }

    public void setDefaultIngredientIds(List<Long> defaultIngredientIds) {
        this.defaultIngredientIds = defaultIngredientIds;
    }
}