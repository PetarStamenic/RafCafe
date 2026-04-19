package com.staticvoid.menuandordersservice.mapper;

import com.staticvoid.menuandordersservice.dto.requests.IngredientRequestDto;
import com.staticvoid.menuandordersservice.dto.response.IngredientResponseDto;
import com.staticvoid.menuandordersservice.model.Ingredient;
import org.springframework.stereotype.Component;

@Component
public class IngredientMapper {

    public Ingredient toEntity(IngredientRequestDto request) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(request.getName());
        ingredient.setExtraPrice(request.getExtraPrice());
        ingredient.setAvailable(request.getAvailable() == null || request.getAvailable());
        return ingredient;
    }

    public void updateEntity(Ingredient ingredient, IngredientRequestDto request) {
        ingredient.setName(request.getName());
        ingredient.setExtraPrice(request.getExtraPrice());

        if (request.getAvailable() != null) {
            ingredient.setAvailable(request.getAvailable());
        }
    }

    public IngredientResponseDto toResponse(Ingredient ingredient) {
        return new IngredientResponseDto(
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getExtraPrice(),
                ingredient.isAvailable()
        );
    }
}