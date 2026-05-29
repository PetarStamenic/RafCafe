package com.staticvoid.menuandordersservice.dto.response;

import com.staticvoid.menuandordersservice.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {

    private Long id;
    private Long customerId;
    private OrderStatus status;
    private String note;
    private Integer loyaltyPointsUsed;
    private BigDecimal loyaltyDiscount;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<OrderItemResponseDto> items = new ArrayList<>();

    public void setItems(List<OrderItemResponseDto> items) {
        this.items = items == null ? new ArrayList<>() : items;
    }
}