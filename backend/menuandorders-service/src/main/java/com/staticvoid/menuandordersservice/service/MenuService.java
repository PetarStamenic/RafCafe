package com.staticvoid.menuandordersservice.service;

import com.staticvoid.menuandordersservice.dto.MenuItemFilterDto;
import com.staticvoid.menuandordersservice.dto.requests.MenuItemRequestDto;
import com.staticvoid.menuandordersservice.dto.response.MenuItemResponseDto;
import com.staticvoid.menuandordersservice.mapper.MenuMapper;
import com.staticvoid.menuandordersservice.model.Ingredient;
import com.staticvoid.menuandordersservice.model.MenuItem;
import com.staticvoid.menuandordersservice.model.MenuItemIngredient;
import com.staticvoid.menuandordersservice.repository.MenuItemRepository;
import com.staticvoid.menuandordersservice.specification.MenuItemSpecification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MenuService {

    private final MenuItemRepository menuItemRepository;
    private final IngredientService ingredientService;

    public MenuService(MenuItemRepository menuItemRepository,
                       IngredientService ingredientService) {
        this.menuItemRepository = menuItemRepository;
        this.ingredientService = ingredientService;
    }

    public List<MenuItemResponseDto> getAll(MenuItemFilterDto filter) {
        return menuItemRepository.findAll(MenuItemSpecification.filterBy(filter))
                .stream()
                .map(MenuMapper::toResponse)
                .toList();
    }

    public MenuItemResponseDto getById(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Menu item not found with id: " + id
                ));

        return MenuMapper.toResponse(menuItem);
    }

    @Transactional
    public MenuItemResponseDto create(MenuItemRequestDto request) {
        if (menuItemRepository.existsByNameIgnoreCase(request.getName())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Menu item with that name already exists"
            );
        }

        validateIngredientLists(request);

        MenuItem menuItem = new MenuItem();
        MenuMapper.updateMenuItemFromRequest(menuItem, request);

        syncIngredients(menuItem, request);

        MenuItem savedMenuItem = menuItemRepository.save(menuItem);
        return MenuMapper.toResponse(savedMenuItem);
    }

    @Transactional
    public MenuItemResponseDto update(Long id, MenuItemRequestDto request) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Menu item not found with id: " + id
                ));

        MenuItem existingWithSameName = menuItemRepository.findByNameIgnoreCase(request.getName())
                .orElse(null);

        if (existingWithSameName != null && !existingWithSameName.getId().equals(id)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Menu item with that name already exists"
            );
        }

        validateIngredientLists(request);

        MenuMapper.updateMenuItemFromRequest(menuItem, request);
        syncIngredients(menuItem, request);

        MenuItem savedMenuItem = menuItemRepository.save(menuItem);
        return MenuMapper.toResponse(savedMenuItem);
    }

    public void delete(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Menu item not found with id: " + id
                ));

        menuItemRepository.delete(menuItem);
    }

    private void validateIngredientLists(MenuItemRequestDto request) {
        Set<Long> allowedIds = new HashSet<>(request.getAllowedIngredientIds());
        Set<Long> defaultIds = new HashSet<>(request.getDefaultIngredientIds());

        if (!allowedIds.containsAll(defaultIds)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Every default ingredient must also be in allowedIngredientIds"
            );
        }
    }

    private void syncIngredients(MenuItem menuItem, MenuItemRequestDto request) {
        Set<Long> defaultIds = new HashSet<>(request.getDefaultIngredientIds());

        menuItem.getAllowedIngredients().clear();

        for (Long ingredientId : request.getAllowedIngredientIds()) {
            Ingredient ingredient = ingredientService.getEntityById(ingredientId);

            MenuItemIngredient link = new MenuItemIngredient();
            link.setMenuItem(menuItem);
            link.setIngredient(ingredient);
            link.setDefaultSelected(defaultIds.contains(ingredientId));

            menuItem.getAllowedIngredients().add(link);
        }
    }
}