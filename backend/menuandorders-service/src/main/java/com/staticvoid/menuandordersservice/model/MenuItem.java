package com.staticvoid.menuandordersservice.model;

import com.staticvoid.menuandordersservice.model.enums.MenuItemType;
import com.staticvoid.menuandordersservice.model.enums.Season;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "menu_items",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name")
        }
)
@Getter
@Setter
@NoArgsConstructor
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MenuItemType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Season season;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal basePrice;

    @Column(nullable = false)
    private boolean available = true;

    @OneToMany(mappedBy = "menuItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuItemIngredient> allowedIngredients = new ArrayList<>();

    public MenuItem(String name, MenuItemType type, Season season, String description, BigDecimal basePrice, boolean available) {
        this.name = name;
        this.type = type;
        this.season = season;
        this.description = description;
        this.basePrice = basePrice;
        this.available = available;
    }

    public void setAllowedIngredients(List<MenuItemIngredient> allowedIngredients) {
        this.allowedIngredients.clear();

        if (allowedIngredients == null) {
            return;
        }

        for (MenuItemIngredient itemIngredient : allowedIngredients) {
            itemIngredient.setMenuItem(this);
            this.allowedIngredients.add(itemIngredient);
        }
    }
}