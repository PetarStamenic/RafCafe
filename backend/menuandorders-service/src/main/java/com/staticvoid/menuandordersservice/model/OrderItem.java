package com.staticvoid.menuandordersservice.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cafe_order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    @Column(nullable = false)
    private String menuItemName;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal basePriceAtOrderTime;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal itemTotalPrice = BigDecimal.ZERO;

    @OneToMany(mappedBy = "orderItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemIngredient> ingredientChanges = new ArrayList<>();

    public OrderItem() {
    }

    public Long getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public String getMenuItemName() {
        return menuItemName;
    }

    public BigDecimal getBasePriceAtOrderTime() {
        return basePriceAtOrderTime;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getItemTotalPrice() {
        return itemTotalPrice;
    }

    public List<OrderItemIngredient> getIngredientChanges() {
        return ingredientChanges;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }

    public void setBasePriceAtOrderTime(BigDecimal basePriceAtOrderTime) {
        this.basePriceAtOrderTime = basePriceAtOrderTime;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setItemTotalPrice(BigDecimal itemTotalPrice) {
        this.itemTotalPrice = itemTotalPrice;
    }

    public void setIngredientChanges(List<OrderItemIngredient> ingredientChanges) {
        this.ingredientChanges.clear();

        if (ingredientChanges == null) {
            return;
        }

        for (OrderItemIngredient change : ingredientChanges) {
            change.setOrderItem(this);
            this.ingredientChanges.add(change);
        }
    }
}