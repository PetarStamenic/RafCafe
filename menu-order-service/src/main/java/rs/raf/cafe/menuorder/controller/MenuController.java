package rs.raf.cafe.menuorder.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.raf.cafe.menuorder.dto.MenuItemRequest;
import rs.raf.cafe.menuorder.dto.MenuItemResponse;
import rs.raf.cafe.menuorder.model.MenuItemType;
import rs.raf.cafe.menuorder.model.Season;
import rs.raf.cafe.menuorder.service.MenuService;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class MenuController {

    private final MenuService menuService;

    @GetMapping
    public ResponseEntity<List<MenuItemResponse>> getAllMenuItems(
            @RequestParam(required = false) MenuItemType type,
            @RequestParam(required = false) Season season) {

        List<MenuItemResponse> items;

        if (type != null) {
            items = menuService.getMenuItemsByType(type);
        } else if (season != null) {
            items = menuService.getMenuItemsBySeason(season);
        } else {
            items = menuService.getAllMenuItems();
        }

        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemResponse> getMenuItemById(@PathVariable Long id) {
        return ResponseEntity.ok(menuService.getMenuItemById(id));
    }

    @PostMapping
    public ResponseEntity<MenuItemResponse> createMenuItem(@Valid @RequestBody MenuItemRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(menuService.createMenuItem(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItemResponse> updateMenuItem(@PathVariable Long id,
                                                           @Valid @RequestBody MenuItemRequest request) {
        return ResponseEntity.ok(menuService.updateMenuItem(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        menuService.deleteMenuItem(id);
        return ResponseEntity.noContent().build();
    }
}
