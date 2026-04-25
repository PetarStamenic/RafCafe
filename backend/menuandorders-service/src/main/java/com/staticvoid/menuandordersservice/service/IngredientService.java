package com.staticvoid.menuandordersservice.service;

import com.staticvoid.menuandordersservice.dto.requests.IngredientRequestDto;
import com.staticvoid.menuandordersservice.dto.response.IngredientResponseDto;
import com.staticvoid.menuandordersservice.mapper.IngredientMapper;
import com.staticvoid.menuandordersservice.model.Ingredient;
import com.staticvoid.menuandordersservice.repository.IngredientRepository;
import com.staticvoid.menuandordersservice.repository.MenuItemIngredientRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final MenuItemIngredientRepository menuItemIngredientRepository;
    private final IngredientMapper ingredientMapper;

    public IngredientService(IngredientRepository ingredientRepository,
                             MenuItemIngredientRepository menuItemIngredientRepository,
                             IngredientMapper ingredientMapper) {
        this.ingredientRepository = ingredientRepository;
        this.menuItemIngredientRepository = menuItemIngredientRepository;
        this.ingredientMapper = ingredientMapper;
    }

    @Transactional(readOnly = true)
    public List<IngredientResponseDto> getAll() {
        return ingredientRepository.findAll()
                .stream()
                .map(ingredientMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public IngredientResponseDto getById(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Ingredient not found with id: " + id
                ));

        return ingredientMapper.toResponse(ingredient);
    }

    @Transactional(readOnly = true)
    public Ingredient getEntityById(Long id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Ingredient not found with id: " + id
                ));
    }

    @Transactional
    public IngredientResponseDto create(IngredientRequestDto request) {
        try {
            Ingredient ingredient = ingredientMapper.toEntity(request);
            Ingredient saved = ingredientRepository.save(ingredient);
            return ingredientMapper.toResponse(saved);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Ingredient name must be unique"
            );
        }
    }

    @Transactional
    public IngredientResponseDto update(Long id, IngredientRequestDto request) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Ingredient not found with id: " + id
                ));

        ingredientMapper.updateEntity(ingredient, request);

        try {
            Ingredient saved = ingredientRepository.save(ingredient);
            return ingredientMapper.toResponse(saved);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Ingredient name must be unique"
            );
        }
    }

    @Transactional
    public void delete(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Ingredient not found with id: " + id
                ));

        if (menuItemIngredientRepository.existsByIngredientId(id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Ingredient is in use and cannot be deleted"
            );
        }

        ingredientRepository.delete(ingredient);
    }
}