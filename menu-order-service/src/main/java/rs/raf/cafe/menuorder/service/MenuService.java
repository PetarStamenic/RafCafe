package rs.raf.cafe.menuorder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.raf.cafe.menuorder.dto.IngredientResponse;
import rs.raf.cafe.menuorder.dto.MenuItemRequest;
import rs.raf.cafe.menuorder.dto.MenuItemResponse;
import rs.raf.cafe.menuorder.exception.ResourceNotFoundException;
import rs.raf.cafe.menuorder.model.Ingredient;
import rs.raf.cafe.menuorder.model.MenuItem;
import rs.raf.cafe.menuorder.model.MenuItemIngredient;
import rs.raf.cafe.menuorder.model.MenuItemType;
import rs.raf.cafe.menuorder.model.Season;
import rs.raf.cafe.menuorder.repository.IngredientRepository;
import rs.raf.cafe.menuorder.repository.MenuItemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuItemRepository menuItemRepository;
    private final IngredientRepository ingredientRepository;

    public List<MenuItemResponse> getAllMenuItems() {
        return menuItemRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public MenuItemResponse getMenuItemById(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + id));
        return toResponse(menuItem);
    }

    public List<MenuItemResponse> getMenuItemsByType(MenuItemType type) {
        return menuItemRepository.findByType(type).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<MenuItemResponse> getMenuItemsBySeason(Season season) {
        return menuItemRepository.findBySeason(season).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public MenuItemResponse createMenuItem(MenuItemRequest request) {
        MenuItem menuItem = MenuItem.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .type(request.getType())
                .season(request.getSeason())
                .available(true)
                .build();

        menuItem = menuItemRepository.save(menuItem);

        if (request.getIngredientIds() != null && !request.getIngredientIds().isEmpty()) {
            List<MenuItemIngredient> menuItemIngredients = new ArrayList<>();
            for (Long ingredientId : request.getIngredientIds()) {
                Ingredient ingredient = ingredientRepository.findById(ingredientId)
                        .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found with id: " + ingredientId));
                MenuItemIngredient mii = MenuItemIngredient.builder()
                        .menuItem(menuItem)
                        .ingredient(ingredient)
                        .build();
                menuItemIngredients.add(mii);
            }
            menuItem.getMenuItemIngredients().addAll(menuItemIngredients);
            menuItem = menuItemRepository.save(menuItem);
        }

        return toResponse(menuItem);
    }

    @Transactional
    public MenuItemResponse updateMenuItem(Long id, MenuItemRequest request) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + id));

        menuItem.setName(request.getName());
        menuItem.setDescription(request.getDescription());
        menuItem.setPrice(request.getPrice());
        menuItem.setType(request.getType());
        menuItem.setSeason(request.getSeason());

        menuItem.getMenuItemIngredients().clear();
        if (request.getIngredientIds() != null && !request.getIngredientIds().isEmpty()) {
            for (Long ingredientId : request.getIngredientIds()) {
                Ingredient ingredient = ingredientRepository.findById(ingredientId)
                        .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found with id: " + ingredientId));
                MenuItemIngredient mii = MenuItemIngredient.builder()
                        .menuItem(menuItem)
                        .ingredient(ingredient)
                        .build();
                menuItem.getMenuItemIngredients().add(mii);
            }
        }

        menuItem = menuItemRepository.save(menuItem);
        return toResponse(menuItem);
    }

    @Transactional
    public void deleteMenuItem(Long id) {
        if (!menuItemRepository.existsById(id)) {
            throw new ResourceNotFoundException("Menu item not found with id: " + id);
        }
        menuItemRepository.deleteById(id);
    }

    private MenuItemResponse toResponse(MenuItem menuItem) {
        List<IngredientResponse> ingredients = menuItem.getMenuItemIngredients().stream()
                .map(mii -> IngredientResponse.builder()
                        .id(mii.getIngredient().getId())
                        .name(mii.getIngredient().getName())
                        .price(mii.getIngredient().getPrice())
                        .available(mii.getIngredient().isAvailable())
                        .build())
                .collect(Collectors.toList());

        return MenuItemResponse.builder()
                .id(menuItem.getId())
                .name(menuItem.getName())
                .description(menuItem.getDescription())
                .price(menuItem.getPrice())
                .type(menuItem.getType())
                .season(menuItem.getSeason())
                .available(menuItem.isAvailable())
                .ingredients(ingredients)
                .build();
    }
}
