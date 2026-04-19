package com.staticvoid.menuandordersservice.dto.requests;

import com.staticvoid.menuandordersservice.model.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateOrderStatusRequestDto {

    @NotNull(message = "Status is required")
    private OrderStatus status;
}