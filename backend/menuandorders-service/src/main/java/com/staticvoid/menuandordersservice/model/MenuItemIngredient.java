package com.staticvoid.menuandordersservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "menu_item_ingredients",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"menu_item_id", "ingredient_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
public class MenuItemIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @Column(nullable = false)
    private boolean defaultSelected = false;

    public MenuItemIngredient(MenuItem menuItem, Ingredient ingredient, boolean defaultSelected) {
        this.menuItem = menuItem;
        this.ingredient = ingredient;
        this.defaultSelected = defaultSelected;
    }
}