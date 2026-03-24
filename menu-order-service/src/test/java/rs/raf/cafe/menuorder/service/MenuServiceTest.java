package rs.raf.cafe.menuorder.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.raf.cafe.menuorder.dto.MenuItemRequest;
import rs.raf.cafe.menuorder.dto.MenuItemResponse;
import rs.raf.cafe.menuorder.exception.ResourceNotFoundException;
import rs.raf.cafe.menuorder.model.*;
import rs.raf.cafe.menuorder.repository.IngredientRepository;
import rs.raf.cafe.menuorder.repository.MenuItemRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private MenuService menuService;

    private MenuItem menuItem;
    private MenuItemRequest menuItemRequest;

    @BeforeEach
    void setUp() {
        menuItem = MenuItem.builder()
                .id(1L)
                .name("Espresso")
                .description("Strong Italian coffee")
                .price(new BigDecimal("3.50"))
                .type(MenuItemType.COFFEE)
                .season(Season.ALL)
                .available(true)
                .menuItemIngredients(new ArrayList<>())
                .build();

        menuItemRequest = MenuItemRequest.builder()
                .name("Espresso")
                .description("Strong Italian coffee")
                .price(new BigDecimal("3.50"))
                .type(MenuItemType.COFFEE)
                .season(Season.ALL)
                .ingredientIds(Collections.emptyList())
                .build();
    }

    @Test
    void getAllMenuItems_returnsAllItems() {
        when(menuItemRepository.findAll()).thenReturn(List.of(menuItem));

        List<MenuItemResponse> result = menuService.getAllMenuItems();

        assertEquals(1, result.size());
        assertEquals("Espresso", result.get(0).getName());
        verify(menuItemRepository).findAll();
    }

    @Test
    void getMenuItemById_existingId_returnsItem() {
        when(menuItemRepository.findById(1L)).thenReturn(Optional.of(menuItem));

        MenuItemResponse result = menuService.getMenuItemById(1L);

        assertEquals("Espresso", result.getName());
        assertEquals(new BigDecimal("3.50"), result.getPrice());
        verify(menuItemRepository).findById(1L);
    }

    @Test
    void getMenuItemById_nonExistingId_throwsException() {
        when(menuItemRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> menuService.getMenuItemById(99L));
        verify(menuItemRepository).findById(99L);
    }

    @Test
    void getMenuItemsByType_returnsFilteredItems() {
        when(menuItemRepository.findByType(MenuItemType.COFFEE)).thenReturn(List.of(menuItem));

        List<MenuItemResponse> result = menuService.getMenuItemsByType(MenuItemType.COFFEE);

        assertEquals(1, result.size());
        assertEquals(MenuItemType.COFFEE, result.get(0).getType());
        verify(menuItemRepository).findByType(MenuItemType.COFFEE);
    }

    @Test
    void getMenuItemsBySeason_returnsFilteredItems() {
        when(menuItemRepository.findBySeason(Season.ALL)).thenReturn(List.of(menuItem));

        List<MenuItemResponse> result = menuService.getMenuItemsBySeason(Season.ALL);

        assertEquals(1, result.size());
        assertEquals(Season.ALL, result.get(0).getSeason());
        verify(menuItemRepository).findBySeason(Season.ALL);
    }

    @Test
    void createMenuItem_validRequest_returnsCreatedItem() {
        when(menuItemRepository.save(any(MenuItem.class))).thenReturn(menuItem);

        MenuItemResponse result = menuService.createMenuItem(menuItemRequest);

        assertNotNull(result);
        assertEquals("Espresso", result.getName());
        assertEquals(MenuItemType.COFFEE, result.getType());
        verify(menuItemRepository, atLeastOnce()).save(any(MenuItem.class));
    }

    @Test
    void updateMenuItem_existingId_returnsUpdatedItem() {
        MenuItemRequest updateRequest = MenuItemRequest.builder()
                .name("Double Espresso")
                .description("Extra strong coffee")
                .price(new BigDecimal("4.50"))
                .type(MenuItemType.COFFEE)
                .season(Season.ALL)
                .ingredientIds(Collections.emptyList())
                .build();

        MenuItem updatedItem = MenuItem.builder()
                .id(1L)
                .name("Double Espresso")
                .description("Extra strong coffee")
                .price(new BigDecimal("4.50"))
                .type(MenuItemType.COFFEE)
                .season(Season.ALL)
                .available(true)
                .menuItemIngredients(new ArrayList<>())
                .build();

        when(menuItemRepository.findById(1L)).thenReturn(Optional.of(menuItem));
        when(menuItemRepository.save(any(MenuItem.class))).thenReturn(updatedItem);

        MenuItemResponse result = menuService.updateMenuItem(1L, updateRequest);

        assertEquals("Double Espresso", result.getName());
        assertEquals(new BigDecimal("4.50"), result.getPrice());
        verify(menuItemRepository).findById(1L);
        verify(menuItemRepository).save(any(MenuItem.class));
    }

    @Test
    void updateMenuItem_nonExistingId_throwsException() {
        when(menuItemRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> menuService.updateMenuItem(99L, menuItemRequest));
        verify(menuItemRepository).findById(99L);
        verify(menuItemRepository, never()).save(any(MenuItem.class));
    }

    @Test
    void deleteMenuItem_existingId_deletesItem() {
        when(menuItemRepository.existsById(1L)).thenReturn(true);
        doNothing().when(menuItemRepository).deleteById(1L);

        assertDoesNotThrow(() -> menuService.deleteMenuItem(1L));
        verify(menuItemRepository).existsById(1L);
        verify(menuItemRepository).deleteById(1L);
    }

    @Test
    void deleteMenuItem_nonExistingId_throwsException() {
        when(menuItemRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> menuService.deleteMenuItem(99L));
        verify(menuItemRepository).existsById(99L);
        verify(menuItemRepository, never()).deleteById(anyLong());
    }
}
