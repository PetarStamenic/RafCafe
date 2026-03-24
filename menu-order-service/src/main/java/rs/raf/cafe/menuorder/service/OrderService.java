package rs.raf.cafe.menuorder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.raf.cafe.menuorder.dto.*;
import rs.raf.cafe.menuorder.exception.BadRequestException;
import rs.raf.cafe.menuorder.exception.ResourceNotFoundException;
import rs.raf.cafe.menuorder.model.MenuItem;
import rs.raf.cafe.menuorder.model.Order;
import rs.raf.cafe.menuorder.model.OrderItem;
import rs.raf.cafe.menuorder.model.OrderStatus;
import rs.raf.cafe.menuorder.repository.MenuItemRepository;
import rs.raf.cafe.menuorder.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MenuItemRepository menuItemRepository;

    @Transactional
    public OrderResponse createOrder(String userEmail, CreateOrderRequest request) {
        Order order = Order.builder()
                .userEmail(userEmail)
                .status(OrderStatus.PENDING)
                .totalPrice(BigDecimal.ZERO)
                .items(new ArrayList<>())
                .build();

        BigDecimal totalPrice = BigDecimal.ZERO;

        for (OrderItemRequest itemRequest : request.getItems()) {
            MenuItem menuItem = menuItemRepository.findById(itemRequest.getMenuItemId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Menu item not found with id: " + itemRequest.getMenuItemId()));

            BigDecimal itemPrice = menuItem.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .menuItem(menuItem)
                    .quantity(itemRequest.getQuantity())
                    .customizationNotes(itemRequest.getCustomizationNotes())
                    .itemPrice(itemPrice)
                    .build();

            order.getItems().add(orderItem);
            totalPrice = totalPrice.add(itemPrice);
        }

        order.setTotalPrice(totalPrice);
        order = orderRepository.save(order);
        return toResponse(order);
    }

    public List<OrderResponse> getOrdersByUser(String userEmail) {
        return orderRepository.findByUserEmail(userEmail).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        return toResponse(order);
    }

    @Transactional
    public OrderResponse cancelOrder(Long id, String userEmail) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        if (!order.getUserEmail().equals(userEmail)) {
            throw new BadRequestException("You can only cancel your own orders");
        }

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new BadRequestException("Only pending orders can be cancelled");
        }

        order.setStatus(OrderStatus.CANCELLED);
        order = orderRepository.save(order);
        return toResponse(order);
    }

    @Transactional
    public OrderResponse updateOrderStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        order.setStatus(status);
        order = orderRepository.save(order);
        return toResponse(order);
    }

    private OrderResponse toResponse(Order order) {
        List<OrderItemResponse> items = order.getItems().stream()
                .map(item -> OrderItemResponse.builder()
                        .id(item.getId())
                        .menuItemName(item.getMenuItem().getName())
                        .quantity(item.getQuantity())
                        .customizationNotes(item.getCustomizationNotes())
                        .itemPrice(item.getItemPrice())
                        .build())
                .collect(Collectors.toList());

        return OrderResponse.builder()
                .id(order.getId())
                .userEmail(order.getUserEmail())
                .status(order.getStatus())
                .totalPrice(order.getTotalPrice())
                .createdAt(order.getCreatedAt())
                .items(items)
                .build();
    }
}
