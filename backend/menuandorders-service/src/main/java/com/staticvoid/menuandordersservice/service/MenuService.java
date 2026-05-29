package com.staticvoid.menuandordersservice.service;

import com.staticvoid.menuandordersservice.dto.filters.MenuItemFilterDto;
import com.staticvoid.menuandordersservice.dto.requests.MenuItemRequestDto;
import com.staticvoid.menuandordersservice.dto.response.MenuItemResponseDto;
import com.staticvoid.menuandordersservice.mapper.MenuMapper;
import com.staticvoid.menuandordersservice.model.Ingredient;
import com.staticvoid.menuandordersservice.model.MenuItem;
import com.staticvoid.menuandordersservice.model.MenuItemIngredient;
import com.staticvoid.menuandordersservice.repository.MenuItemRepository;
import com.staticvoid.menuandordersservice.specification.MenuItemSpecification;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class MenuService {

    private final MenuItemRepository menuItemRepository;
    private final IngredientService ingredientService;
    private final MenuMapper menuMapper;

    public MenuService(MenuItemRepository menuItemRepository,
                       IngredientService ingredientService,
                       MenuMapper menuMapper) {
        this.menuItemRepository = menuItemRepository;
        this.ingredientService = ingredientService;
        this.menuMapper = menuMapper;
    }

    @Transactional(readOnly = true)
    public List<MenuItemResponseDto> getAll(MenuItemFilterDto filter) {
        return menuItemRepository.findAll(MenuItemSpecification.filterBy(filter))
                .stream()
                .map(menuMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public MenuItemResponseDto getById(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Menu item not found with id: " + id
                ));

        return menuMapper.toResponse(menuItem);
    }

    @Transactional
    public MenuItemResponseDto create(MenuItemRequestDto request) {
        validateIngredientLists(request);

        MenuItem menuItem = new MenuItem();
        menuMapper.updateMenuItemFromRequest(menuItem, request);
        syncIngredients(menuItem, request);

        try {
            MenuItem savedMenuItem = menuItemRepository.save(menuItem);
            return menuMapper.toResponse(savedMenuItem);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Menu item name must be unique"
            );
        }
    }

    @Transactional
    public MenuItemResponseDto update(Long id, MenuItemRequestDto request) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Menu item not found with id: " + id
                ));

        validateIngredientLists(request);

        menuMapper.updateMenuItemFromRequest(menuItem, request);
        syncIngredients(menuItem, request);

        try {
            MenuItem savedMenuItem = menuItemRepository.save(menuItem);
            return menuMapper.toResponse(savedMenuItem);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Menu item name must be unique"
            );
        }
    }

    @Transactional
    public void delete(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Menu item not found with id: " + id
                ));

        menuItemRepository.delete(menuItem);
    }

    private void validateIngredientLists(MenuItemRequestDto request) {
        Set<Long> allowedIds = normalizeIds(request.getAllowedIngredientIds());
        Set<Long> defaultIds = normalizeIds(request.getDefaultIngredientIds());

        if (!allowedIds.containsAll(defaultIds)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Every default ingredient must also be in allowedIngredientIds"
            );
        }
    }

    private void syncIngredients(MenuItem menuItem, MenuItemRequestDto request) {
        Set<Long> allowedIds = normalizeIds(request.getAllowedIngredientIds());
        Set<Long> defaultIds = normalizeIds(request.getDefaultIngredientIds());

        menuItem.getAllowedIngredients().clear();

        for (Long ingredientId : allowedIds) {
            Ingredient ingredient = ingredientService.getEntityById(ingredientId);

            MenuItemIngredient link = new MenuItemIngredient();
            link.setMenuItem(menuItem);
            link.setIngredient(ingredient);
            link.setDefaultSelected(defaultIds.contains(ingredientId));

            menuItem.getAllowedIngredients().add(link);
        }
    }

    private Set<Long> normalizeIds(List<Long> ids) {
        if (ids == null) {
            return new LinkedHashSet<>();
        }
        return new LinkedHashSet<>(ids);
    }
}