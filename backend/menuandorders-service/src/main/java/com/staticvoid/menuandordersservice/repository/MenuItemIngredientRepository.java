package com.staticvoid.menuandordersservice.repository;

import com.staticvoid.menuandordersservice.model.MenuItemIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuItemIngredientRepository extends JpaRepository<MenuItemIngredient, Long> {

    List<MenuItemIngredient> findByMenuItemId(Long menuItemId);

    Optional<MenuItemIngredient> findByMenuItemIdAndIngredientId(Long menuItemId, Long ingredientId);

    boolean existsByIngredientId(Long ingredientId);

    void deleteByMenuItemId(Long menuItemId);
}