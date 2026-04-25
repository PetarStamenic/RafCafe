package com.staticvoid.menuandordersservice.dto.response;

import com.staticvoid.menuandordersservice.model.enums.MenuItemType;
import com.staticvoid.menuandordersservice.model.enums.Season;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public void setAllowedIngredients(List<IngredientResponseDto> allowedIngredients) {
        this.allowedIngredients = allowedIngredients == null ? new ArrayList<>() : allowedIngredients;
    }

    public void setDefaultIngredients(List<IngredientResponseDto> defaultIngredients) {
        this.defaultIngredients = defaultIngredients == null ? new ArrayList<>() : defaultIngredients;
    }
}