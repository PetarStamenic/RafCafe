package com.staticvoid.menuandordersservice.model;

import com.staticvoid.menuandordersservice.model.enums.OrderStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cafe_orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long customerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.CREATED;

    @Column(length = 1000)
    private String note;

    @Column(nullable = false)
    private Integer loyaltyPointsUsed = 0;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal loyaltyDiscount = BigDecimal.ZERO;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice = BigDecimal.ZERO;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    public Order() {
    }

    @PrePersist
    public void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public String getNote() {
        return note;
    }

    public Integer getLoyaltyPointsUsed() {
        return loyaltyPointsUsed;
    }

    public BigDecimal getLoyaltyDiscount() {
        return loyaltyDiscount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setLoyaltyPointsUsed(Integer loyaltyPointsUsed) {
        this.loyaltyPointsUsed = loyaltyPointsUsed;
    }

    public void setLoyaltyDiscount(BigDecimal loyaltyDiscount) {
        this.loyaltyDiscount = loyaltyDiscount;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setItems(List<OrderItem> items) {
        this.items.clear();

        if (items == null) {
            return;
        }

        for (OrderItem item : items) {
            item.setOrder(this);
            this.items.add(item);
        }
    }
}