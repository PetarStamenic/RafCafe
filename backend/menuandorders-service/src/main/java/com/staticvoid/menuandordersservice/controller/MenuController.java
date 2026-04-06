package com.staticvoid.menuandordersservice.controller;

import com.staticvoid.menuandordersservice.dto.MenuItemFilterDto;
import com.staticvoid.menuandordersservice.dto.requests.MenuItemRequestDto;
import com.staticvoid.menuandordersservice.dto.response.MenuItemResponseDto;
import com.staticvoid.menuandordersservice.model.enums.MenuItemType;
import com.staticvoid.menuandordersservice.model.enums.Season;
import com.staticvoid.menuandordersservice.service.MenuService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public List<MenuItemResponseDto> getAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) MenuItemType type,
            @RequestParam(required = false) Season season,
            @RequestParam(required = false) Boolean available,
            @RequestParam(required = false) Long ingredientId
    ) {
        MenuItemFilterDto filter = new MenuItemFilterDto();
        filter.setName(name);
        filter.setType(type);
        filter.setSeason(season);
        filter.setAvailable(available);
        filter.setIngredientId(ingredientId);

        return menuService.getAll(filter);
    }

    @GetMapping("/{id}")
    public MenuItemResponseDto getById(@PathVariable Long id) {
        return menuService.getById(id);
    }

    @PostMapping
    public MenuItemResponseDto create(@Valid @RequestBody MenuItemRequestDto request) {
        return menuService.create(request);
    }

    @PutMapping("/{id}")
    public MenuItemResponseDto update(@PathVariable Long id,
                                      @Valid @RequestBody MenuItemRequestDto request) {
        return menuService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        menuService.delete(id);
    }
}