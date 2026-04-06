package com.staticvoid.menuandordersservice.dto;

import com.staticvoid.menuandordersservice.model.enums.MenuItemType;
import com.staticvoid.menuandordersservice.model.enums.Season;

public class MenuItemFilterDto {

    private String name;
    private MenuItemType type;
    private Season season;
    private Boolean available;
    private Long ingredientId;

    public MenuItemFilterDto() {
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

    public Boolean getAvailable() {
        return available;
    }

    public Long getIngredientId() {
        return ingredientId;
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

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }
}