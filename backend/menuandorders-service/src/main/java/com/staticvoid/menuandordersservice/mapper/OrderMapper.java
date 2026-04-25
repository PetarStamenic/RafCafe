package com.staticvoid.menuandordersservice.mapper;

import com.staticvoid.menuandordersservice.dto.response.OrderItemIngredientResponseDto;
import com.staticvoid.menuandordersservice.dto.response.OrderItemResponseDto;
import com.staticvoid.menuandordersservice.dto.response.OrderResponseDto;
import com.staticvoid.menuandordersservice.model.Order;
import com.staticvoid.menuandordersservice.model.OrderItem;
import com.staticvoid.menuandordersservice.model.OrderItemIngredient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapper {

    public OrderResponseDto toResponse(Order order) {
        OrderResponseDto response = new OrderResponseDto();
        response.setId(order.getId());
        response.setCustomerId(order.getCustomerId());
        response.setStatus(order.getStatus());
        response.setNote(order.getNote());
        response.setLoyaltyPointsUsed(order.getLoyaltyPointsUsed());
        response.setLoyaltyDiscount(order.getLoyaltyDiscount());
        response.setTotalPrice(order.getTotalPrice());
        response.setCreatedAt(order.getCreatedAt());
        response.setUpdatedAt(order.getUpdatedAt());

        List<OrderItemResponseDto> itemResponses = new ArrayList<>();
        for (OrderItem item : order.getItems()) {
            itemResponses.add(toOrderItemResponse(item));
        }

        response.setItems(itemResponses);
        return response;
    }

    private OrderItemResponseDto toOrderItemResponse(OrderItem item) {
        OrderItemResponseDto response = new OrderItemResponseDto();
        response.setId(item.getId());
        response.setMenuItemId(item.getMenuItem().getId());
        response.setMenuItemName(item.getMenuItemName());
        response.setBasePriceAtOrderTime(item.getBasePriceAtOrderTime());
        response.setQuantity(item.getQuantity());
        response.setItemTotalPrice(item.getItemTotalPrice());

        List<OrderItemIngredientResponseDto> ingredientResponses = new ArrayList<>();
        for (OrderItemIngredient change : item.getIngredientChanges()) {
            ingredientResponses.add(new OrderItemIngredientResponseDto(
                    change.getIngredient().getId(),
                    change.getIngredient().getName(),
                    change.getIngredient().getExtraPrice(),
                    change.getChangeType()
            ));
        }

        response.setIngredientChanges(ingredientResponses);
        return response;
    }
}