package com.staticvoid.menuandordersservice.controller;

import com.staticvoid.menuandordersservice.dto.filters.OrderFilterDto;
import com.staticvoid.menuandordersservice.dto.requests.CreateOrderRequestDto;
import com.staticvoid.menuandordersservice.dto.requests.UpdateOrderRequestDto;
import com.staticvoid.menuandordersservice.dto.requests.UpdateOrderStatusRequestDto;
import com.staticvoid.menuandordersservice.dto.response.OrderResponseDto;
import com.staticvoid.menuandordersservice.model.enums.OrderStatus;
import com.staticvoid.menuandordersservice.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<OrderResponseDto> getAll(
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) OrderStatus status
    ) {
        OrderFilterDto filter = new OrderFilterDto();
        filter.setCustomerId(customerId);
        filter.setStatus(status);

        return orderService.getAll(filter);
    }

    @GetMapping("/{id}")
    public OrderResponseDto getById(@PathVariable Long id) {
        return orderService.getById(id);
    }

    @PostMapping
    public OrderResponseDto create(@Valid @RequestBody CreateOrderRequestDto request) {
        return orderService.create(request);
    }

    @PutMapping("/{id}")
    public OrderResponseDto update(@PathVariable Long id,
                                   @Valid @RequestBody UpdateOrderRequestDto request) {
        return orderService.update(id, request);
    }

    @PatchMapping("/{id}/status")
    public OrderResponseDto updateStatus(@PathVariable Long id,
                                         @Valid @RequestBody UpdateOrderStatusRequestDto request) {
        return orderService.updateStatus(id, request);
    }

    @PatchMapping("/{id}/cancel")
    public OrderResponseDto cancel(@PathVariable Long id) {
        return orderService.cancel(id);
    }
}