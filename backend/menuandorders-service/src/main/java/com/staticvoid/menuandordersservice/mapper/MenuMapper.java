package com.staticvoid.menuandordersservice.mapper;

import com.staticvoid.menuandordersservice.dto.requests.MenuItemRequestDto;
import com.staticvoid.menuandordersservice.dto.response.IngredientResponseDto;
import com.staticvoid.menuandordersservice.dto.response.MenuItemResponseDto;
import com.staticvoid.menuandordersservice.model.MenuItem;
import com.staticvoid.menuandordersservice.model.MenuItemIngredient;

import java.util.ArrayList;
import java.util.List;

public class MenuMapper {

    private MenuMapper() {
    }

    public static void updateMenuItemFromRequest(MenuItem menuItem, MenuItemRequestDto request) {
        menuItem.setName(request.getName());
        menuItem.setType(request.getType());
        menuItem.setSeason(request.getSeason());
        menuItem.setDescription(request.getDescription());
        menuItem.setBasePrice(request.getBasePrice());
        menuItem.setAvailable(request.isAvailable());
    }

    public static MenuItemResponseDto toResponse(MenuItem menuItem) {
        MenuItemResponseDto response = new MenuItemResponseDto();
        response.setId(menuItem.getId());
        response.setName(menuItem.getName());
        response.setType(menuItem.getType());
        response.setSeason(menuItem.getSeason());
        response.setDescription(menuItem.getDescription());
        response.setBasePrice(menuItem.getBasePrice());
        response.setAvailable(menuItem.isAvailable());

        List<IngredientResponseDto> allowed = new ArrayList<>();
        List<IngredientResponseDto> defaults = new ArrayList<>();

        for (MenuItemIngredient link : menuItem.getAllowedIngredients()) {
            IngredientResponseDto ingredientDto = new IngredientResponseDto(
                    link.getIngredient().getId(),
                    link.getIngredient().getName(),
                    link.getIngredient().getExtraPrice(),
                    link.getIngredient().isAvailable()
            );

            allowed.add(ingredientDto);

            if (link.isDefaultSelected()) {
                defaults.add(ingredientDto);
            }
        }

        response.setAllowedIngredients(allowed);
        response.setDefaultIngredients(defaults);

        return response;
    }
}