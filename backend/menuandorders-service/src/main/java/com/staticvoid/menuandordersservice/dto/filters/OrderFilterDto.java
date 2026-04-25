package com.staticvoid.menuandordersservice.dto.filters;

import com.staticvoid.menuandordersservice.model.enums.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderFilterDto {

    private Long customerId;
    private OrderStatus status;
}