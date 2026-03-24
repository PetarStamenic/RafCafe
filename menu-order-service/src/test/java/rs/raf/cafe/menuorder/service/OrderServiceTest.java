package rs.raf.cafe.menuorder.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.raf.cafe.menuorder.dto.CreateOrderRequest;
import rs.raf.cafe.menuorder.dto.OrderItemRequest;
import rs.raf.cafe.menuorder.dto.OrderResponse;
import rs.raf.cafe.menuorder.exception.BadRequestException;
import rs.raf.cafe.menuorder.exception.ResourceNotFoundException;
import rs.raf.cafe.menuorder.model.*;
import rs.raf.cafe.menuorder.repository.MenuItemRepository;
import rs.raf.cafe.menuorder.repository.OrderRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private MenuItemRepository menuItemRepository;

    @InjectMocks
    private OrderService orderService;

    private MenuItem menuItem;
    private Order order;

    @BeforeEach
    void setUp() {
        menuItem = MenuItem.builder()
                .id(1L)
                .name("Espresso")
                .description("Strong Italian coffee")
                .price(new BigDecimal("3.50"))
                .type(MenuItemType.COFFEE)
                .season(Season.ALL)
                .available(true)
                .menuItemIngredients(new ArrayList<>())
                .build();

        OrderItem orderItem = OrderItem.builder()
                .id(1L)
                .menuItem(menuItem)
                .quantity(2)
                .customizationNotes("Extra hot")
                .itemPrice(new BigDecimal("7.00"))
                .build();

        order = Order.builder()
                .id(1L)
                .userEmail("user@test.com")
                .status(OrderStatus.PENDING)
                .totalPrice(new BigDecimal("7.00"))
                .createdAt(LocalDateTime.now())
                .items(new ArrayList<>(List.of(orderItem)))
                .build();

        orderItem.setOrder(order);
    }

    @Test
    void createOrder_validRequest_returnsOrderWithCalculatedPrice() {
        OrderItemRequest itemRequest = OrderItemRequest.builder()
                .menuItemId(1L)
                .quantity(2)
                .customizationNotes("Extra hot")
                .build();

        CreateOrderRequest request = CreateOrderRequest.builder()
                .items(List.of(itemRequest))
                .build();

        when(menuItemRepository.findById(1L)).thenReturn(Optional.of(menuItem));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order savedOrder = invocation.getArgument(0);
            savedOrder.setId(1L);
            savedOrder.setCreatedAt(LocalDateTime.now());
            return savedOrder;
        });

        OrderResponse result = orderService.createOrder("user@test.com", request);

        assertNotNull(result);
        assertEquals("user@test.com", result.getUserEmail());
        assertEquals(OrderStatus.PENDING, result.getStatus());
        assertEquals(0, new BigDecimal("7.00").compareTo(result.getTotalPrice()));
        assertEquals(1, result.getItems().size());
        verify(menuItemRepository).findById(1L);
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void getOrdersByUser_returnsUserOrders() {
        when(orderRepository.findByUserEmail("user@test.com")).thenReturn(List.of(order));

        List<OrderResponse> result = orderService.getOrdersByUser("user@test.com");

        assertEquals(1, result.size());
        assertEquals("user@test.com", result.get(0).getUserEmail());
        verify(orderRepository).findByUserEmail("user@test.com");
    }

    @Test
    void getOrderById_existingId_returnsOrder() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        OrderResponse result = orderService.getOrderById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("user@test.com", result.getUserEmail());
        verify(orderRepository).findById(1L);
    }

    @Test
    void getOrderById_nonExistingId_throwsException() {
        when(orderRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.getOrderById(99L));
        verify(orderRepository).findById(99L);
    }

    @Test
    void cancelOrder_pendingOrder_cancelsSuccessfully() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        OrderResponse result = orderService.cancelOrder(1L, "user@test.com");

        assertEquals(OrderStatus.CANCELLED, result.getStatus());
        verify(orderRepository).findById(1L);
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void cancelOrder_nonPendingOrder_throwsException() {
        order.setStatus(OrderStatus.PREPARING);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        assertThrows(BadRequestException.class,
                () -> orderService.cancelOrder(1L, "user@test.com"));
        verify(orderRepository).findById(1L);
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void updateOrderStatus_existingOrder_updatesStatus() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        OrderResponse result = orderService.updateOrderStatus(1L, OrderStatus.PREPARING);

        assertEquals(OrderStatus.PREPARING, result.getStatus());
        verify(orderRepository).findById(1L);
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void updateOrderStatus_nonExistingOrder_throwsException() {
        when(orderRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> orderService.updateOrderStatus(99L, OrderStatus.PREPARING));
        verify(orderRepository).findById(99L);
    }
}
