package com.staticvoid.menuandordersservice.dto.response;

import com.staticvoid.menuandordersservice.model.enums.MenuItemType;
import com.staticvoid.menuandordersservice.model.enums.Season;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MenuItemResponseDto {

    private Long id;
    private String name;
    private MenuItemType type;
    private Season season;
    private String description;
    private BigDecimal basePrice;
    private boolean available;
    private List<IngredientResponseDto> allowedIngredients = new ArrayList<>();
    private List<IngredientResponseDto> defaultIngredients = new ArrayList<>();

    public MenuItemResponseDto() {
    }

    public Long getId() {
        return id;
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

    public List<IngredientResponseDto> getAllowedIngredients() {
        return allowedIngredients;
    }

    public List<IngredientResponseDto> getDefaultIngredients() {
        return defaultIngredients;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setAllowedIngredients(List<IngredientResponseDto> allowedIngredients) {
        this.allowedIngredients = allowedIngredients;
    }

    public void setDefaultIngredients(List<IngredientResponseDto> defaultIngredients) {
        this.defaultIngredients = defaultIngredients;
    }
}