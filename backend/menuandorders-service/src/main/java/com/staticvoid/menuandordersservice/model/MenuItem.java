package com.staticvoid.menuandordersservice.model;

import com.staticvoid.menuandordersservice.model.enums.MenuItemType;
import com.staticvoid.menuandordersservice.model.enums.Season;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menu_items")
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

    public MenuItem() {
    }

    public MenuItem(String name, MenuItemType type, Season season, String description, BigDecimal basePrice, boolean available) {
        this.name = name;
        this.type = type;
        this.season = season;
        this.description = description;
        this.basePrice = basePrice;
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public MenuItemType getType() {
        return type;
    }

    public Season getSeason() {
        return season;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public boolean isAvailable() {
        return available;
    }

    public List<MenuItemIngredient> getAllowedIngredients() {
        return allowedIngredients;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(MenuItemType type) {
        this.type = type;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setAllowedIngredients(List<MenuItemIngredient> allowedIngredients) {
        this.allowedIngredients = allowedIngredients;
    }
}