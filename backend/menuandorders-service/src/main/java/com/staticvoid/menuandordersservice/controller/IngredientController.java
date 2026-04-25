package com.staticvoid.menuandordersservice.controller;

import com.staticvoid.menuandordersservice.dto.requests.IngredientRequestDto;
import com.staticvoid.menuandordersservice.dto.response.IngredientResponseDto;
import com.staticvoid.menuandordersservice.service.IngredientService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public List<IngredientResponseDto> getAll() {
        return ingredientService.getAll();
    }

    @GetMapping("/{id}")
    public IngredientResponseDto getById(@PathVariable Long id) {
        return ingredientService.getById(id);
    }

    @PostMapping
    public IngredientResponseDto create(@Valid @RequestBody IngredientRequestDto request) {
        return ingredientService.create(request);
    }

    @PutMapping("/{id}")
    public IngredientResponseDto update(@PathVariable Long id,
                                        @Valid @RequestBody IngredientRequestDto request) {
        return ingredientService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        ingredientService.delete(id);
    }
}