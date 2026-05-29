package com.staticvoid.menuandordersservice.model;

import com.staticvoid.menuandordersservice.model.enums.IngredientChangeType;
import jakarta.persistence.*;

@Entity
@Table(name = "cafe_order_item_ingredients")
public class OrderItemIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IngredientChangeType changeType;

    public OrderItemIngredient() {
    }

    public Long getId() {
        return id;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public IngredientChangeType getChangeType() {
        return changeType;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public void setChangeType(IngredientChangeType changeType) {
        this.changeType = changeType;
    }
}