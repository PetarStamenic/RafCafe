package com.staticvoid.menuandordersservice.dto.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UpdateOrderRequestDto {

    @NotNull(message = "Customer id is required")
    private Long customerId;

    private String note;

    @Min(value = 0, message = "Loyalty points used cannot be negative")
    private Integer loyaltyPointsUsed = 0;

    @Valid
    @NotEmpty(message = "Order must contain at least one item")
    private List<CreateOrderItemRequestDto> items = new ArrayList<>();

    public void setLoyaltyPointsUsed(Integer loyaltyPointsUsed) {
        this.loyaltyPointsUsed = loyaltyPointsUsed == null ? 0 : loyaltyPointsUsed;
    }

    public void setItems(List<CreateOrderItemRequestDto> items) {
        this.items = items == null ? new ArrayList<>() : items;
    }
}