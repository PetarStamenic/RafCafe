package com.staticvoid.menuandordersservice.service;

import com.staticvoid.menuandordersservice.dto.requests.IngredientRequestDto;
import com.staticvoid.menuandordersservice.dto.response.IngredientResponseDto;
import com.staticvoid.menuandordersservice.model.Ingredient;
import com.staticvoid.menuandordersservice.repository.IngredientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public List<IngredientResponseDto> getAll() {
        return ingredientRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public IngredientResponseDto getById(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Ingredient not found with id: " + id
                ));

        return toResponse(ingredient);
    }

    public Ingredient getEntityById(Long id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Ingredient not found with id: " + id
                ));
    }

    public IngredientResponseDto create(IngredientRequestDto request) {
        if (ingredientRepository.existsByNameIgnoreCase(request.getName())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Ingredient with that name already exists"
            );
        }

        Ingredient ingredient = new Ingredient();
        ingredient.setName(request.getName());
        ingredient.setExtraPrice(request.getExtraPrice());
        ingredient.setAvailable(request.isAvailable());

        Ingredient saved = ingredientRepository.save(ingredient);
        return toResponse(saved);
    }

    public IngredientResponseDto update(Long id, IngredientRequestDto request) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Ingredient not found with id: " + id
                ));

        Ingredient existingWithSameName = ingredientRepository.findByNameIgnoreCase(request.getName())
                .orElse(null);

        if (existingWithSameName != null && !existingWithSameName.getId().equals(id)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Ingredient with that name already exists"
            );
        }

        ingredient.setName(request.getName());
        ingredient.setExtraPrice(request.getExtraPrice());
        ingredient.setAvailable(request.isAvailable());

        Ingredient saved = ingredientRepository.save(ingredient);
        return toResponse(saved);
    }

    public void delete(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Ingredient not found with id: " + id
                ));

        ingredientRepository.delete(ingredient);
    }

    private IngredientResponseDto toResponse(Ingredient ingredient) {
        return new IngredientResponseDto(
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getExtraPrice(),
                ingredient.isAvailable()
        );
    }
}