package com.staticvoid.menuandordersservice.service;

import com.staticvoid.menuandordersservice.dto.filters.OrderFilterDto;
import com.staticvoid.menuandordersservice.dto.requests.CreateOrderItemRequestDto;
import com.staticvoid.menuandordersservice.dto.requests.CreateOrderRequestDto;
import com.staticvoid.menuandordersservice.dto.requests.UpdateOrderRequestDto;
import com.staticvoid.menuandordersservice.dto.requests.UpdateOrderStatusRequestDto;
import com.staticvoid.menuandordersservice.dto.response.OrderResponseDto;
import com.staticvoid.menuandordersservice.mapper.OrderMapper;
import com.staticvoid.menuandordersservice.model.Ingredient;
import com.staticvoid.menuandordersservice.model.MenuItem;
import com.staticvoid.menuandordersservice.model.MenuItemIngredient;
import com.staticvoid.menuandordersservice.model.Order;
import com.staticvoid.menuandordersservice.model.OrderItem;
import com.staticvoid.menuandordersservice.model.OrderItemIngredient;
import com.staticvoid.menuandordersservice.model.enums.IngredientChangeType;
import com.staticvoid.menuandordersservice.model.enums.OrderStatus;
import com.staticvoid.menuandordersservice.repository.MenuItemRepository;
import com.staticvoid.menuandordersservice.repository.OrderRepository;
import com.staticvoid.menuandordersservice.specification.OrderSpecification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderService {

    private static final BigDecimal LOYALTY_POINT_VALUE = new BigDecimal("0.01");

    private final OrderRepository orderRepository;
    private final MenuItemRepository menuItemRepository;
    private final IngredientService ingredientService;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository,
                        MenuItemRepository menuItemRepository,
                        IngredientService ingredientService,
                        OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.menuItemRepository = menuItemRepository;
        this.ingredientService = ingredientService;
        this.orderMapper = orderMapper;
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDto> getAll(OrderFilterDto filter) {
        return orderRepository.findAll(OrderSpecification.filterBy(filter))
                .stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public OrderResponseDto getById(Long id) {
        Order order = findOrderEntity(id);
        return orderMapper.toResponse(order);
    }

    @Transactional
    public OrderResponseDto create(CreateOrderRequestDto request) {
        Order order = new Order();
        applyOrderData(order, request.getCustomerId(), request.getNote(), request.getLoyaltyPointsUsed(), request.getItems());

        Order savedOrder = orderRepository.save(order);
        return orderMapper.toResponse(savedOrder);
    }

    @Transactional
    public OrderResponseDto update(Long id, UpdateOrderRequestDto request) {
        Order order = findOrderEntity(id);
        ensureOrderEditable(order);

        applyOrderData(order, request.getCustomerId(), request.getNote(), request.getLoyaltyPointsUsed(), request.getItems());

        Order savedOrder = orderRepository.save(order);
        return orderMapper.toResponse(savedOrder);
    }

    @Transactional
    public OrderResponseDto updateStatus(Long id, UpdateOrderStatusRequestDto request) {
        Order order = findOrderEntity(id);
        OrderStatus currentStatus = order.getStatus();
        OrderStatus newStatus = request.getStatus();

        if (currentStatus == OrderStatus.CANCELLED) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Cannot change status of a cancelled order"
            );
        }

        if (currentStatus == OrderStatus.COMPLETED) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Cannot change status of a completed order"
            );
        }

        if (newStatus == OrderStatus.CANCELLED) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Use the cancel endpoint to cancel an order"
            );
        }

        if (currentStatus == newStatus) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Order is already in status " + newStatus
            );
        }

        validateStatusTransition(currentStatus, newStatus);

        order.setStatus(newStatus);

        Order savedOrder = orderRepository.save(order);
        return orderMapper.toResponse(savedOrder);
    }

    @Transactional
    public OrderResponseDto cancel(Long id) {
        Order order = findOrderEntity(id);

        if (order.getStatus() == OrderStatus.COMPLETED) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Completed orders cannot be cancelled"
            );
        }

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Order is already cancelled"
            );
        }

        order.setStatus(OrderStatus.CANCELLED);

        Order savedOrder = orderRepository.save(order);
        return orderMapper.toResponse(savedOrder);
    }

    private Order findOrderEntity(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Order not found with id: " + id
                ));
    }

    private void ensureOrderEditable(Order order) {
        if (order.getStatus() == OrderStatus.COMPLETED) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Completed orders cannot be edited"
            );
        }

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Cancelled orders cannot be edited"
            );
        }
    }

    private void validateStatusTransition(OrderStatus currentStatus, OrderStatus newStatus) {
        boolean valid =
                (currentStatus == OrderStatus.CREATED && newStatus == OrderStatus.IN_PROGRESS) ||
                        (currentStatus == OrderStatus.IN_PROGRESS && (newStatus == OrderStatus.READY || newStatus == OrderStatus.COMPLETED)) ||
                        (currentStatus == OrderStatus.READY && newStatus == OrderStatus.COMPLETED);

        if (!valid) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid status transition from " + currentStatus + " to " + newStatus
            );
        }
    }

    private void applyOrderData(Order order,
                                Long customerId,
                                String note,
                                Integer loyaltyPointsUsed,
                                List<CreateOrderItemRequestDto> itemRequests) {

        int effectivePoints = getVerifiedLoyaltyPointsToUse(customerId, loyaltyPointsUsed);

        order.setCustomerId(customerId);
        order.setNote(note);
        order.setLoyaltyPointsUsed(effectivePoints);

        List<OrderItem> rebuiltItems = new ArrayList<>();
        BigDecimal subtotal = BigDecimal.ZERO;

        for (CreateOrderItemRequestDto itemRequest : itemRequests) {
            OrderItem orderItem = buildOrderItem(itemRequest);
            orderItem.setOrder(order);
            rebuiltItems.add(orderItem);
            subtotal = subtotal.add(orderItem.getItemTotalPrice());
        }

        order.setItems(rebuiltItems);

        BigDecimal loyaltyDiscount = calculateLoyaltyDiscount(effectivePoints, subtotal);
        order.setLoyaltyDiscount(loyaltyDiscount);
        order.setTotalPrice(subtotal.subtract(loyaltyDiscount));
    }


    private int getVerifiedLoyaltyPointsToUse(Long customerId, Integer requestedPoints) {
        if (requestedPoints == null || requestedPoints == 0) {
            return 0;
        }

        if (requestedPoints < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Loyalty points used cannot be negative"
            );
        }

        int availablePoints = getAvailableLoyaltyPointsForCustomer(customerId);

        if (requestedPoints > availablePoints) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "User does not have enough loyalty points"
            );
        }

        return requestedPoints;
    }

    private int getAvailableLoyaltyPointsForCustomer(Long customerId) {
        //will implement when services communicate
        return 0;
    }

    private OrderItem buildOrderItem(CreateOrderItemRequestDto request) {
        MenuItem menuItem = menuItemRepository.findById(request.getMenuItemId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Menu item not found with id: " + request.getMenuItemId()
                ));

        if (!menuItem.isAvailable()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Menu item is not available: " + menuItem.getName()
            );
        }

        Set<Long> allowedIngredientIds = new LinkedHashSet<>();
        Set<Long> defaultIngredientIds = new LinkedHashSet<>();

        for (MenuItemIngredient link : menuItem.getAllowedIngredients()) {
            Long ingredientId = link.getIngredient().getId();
            allowedIngredientIds.add(ingredientId);

            if (link.isDefaultSelected()) {
                defaultIngredientIds.add(ingredientId);
            }
        }

        Set<Long> addedIds = normalizeIds(request.getAddedIngredientIds());
        Set<Long> removedIds = normalizeIds(request.getRemovedIngredientIds());

        validateIngredientChanges(menuItem, allowedIngredientIds, defaultIngredientIds, addedIds, removedIds);

        OrderItem orderItem = new OrderItem();
        orderItem.setMenuItem(menuItem);
        orderItem.setMenuItemName(menuItem.getName());
        orderItem.setBasePriceAtOrderTime(menuItem.getBasePrice());
        orderItem.setQuantity(request.getQuantity());

        BigDecimal oneItemPrice = menuItem.getBasePrice();

        for (Long addedId : addedIds) {
            Ingredient ingredient = ingredientService.getEntityById(addedId);

            OrderItemIngredient change = new OrderItemIngredient();
            change.setOrderItem(orderItem);
            change.setIngredient(ingredient);
            change.setChangeType(IngredientChangeType.ADD);
            orderItem.getIngredientChanges().add(change);

            oneItemPrice = oneItemPrice.add(ingredient.getExtraPrice());
        }

        for (Long removedId : removedIds) {
            Ingredient ingredient = ingredientService.getEntityById(removedId);

            OrderItemIngredient change = new OrderItemIngredient();
            change.setOrderItem(orderItem);
            change.setIngredient(ingredient);
            change.setChangeType(IngredientChangeType.REMOVE);
            orderItem.getIngredientChanges().add(change);
        }

        BigDecimal itemTotalPrice = oneItemPrice.multiply(BigDecimal.valueOf(request.getQuantity()));
        orderItem.setItemTotalPrice(itemTotalPrice);

        return orderItem;
    }

    private void validateIngredientChanges(MenuItem menuItem,
                                           Set<Long> allowedIngredientIds,
                                           Set<Long> defaultIngredientIds,
                                           Set<Long> addedIds,
                                           Set<Long> removedIds) {

        for (Long addedId : addedIds) {
            if (!allowedIngredientIds.contains(addedId)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Ingredient " + addedId + " is not allowed for menu item " + menuItem.getName()
                );
            }

            if (defaultIngredientIds.contains(addedId)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Ingredient " + addedId + " is already included by default in " + menuItem.getName()
                );
            }
        }

        for (Long removedId : removedIds) {
            if (!defaultIngredientIds.contains(removedId)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Ingredient " + removedId + " is not a default ingredient of " + menuItem.getName()
                );
            }
        }

        for (Long removedId : removedIds) {
            if (addedIds.contains(removedId)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Ingredient " + removedId + " cannot be both added and removed"
                );
            }
        }
    }

    private BigDecimal calculateLoyaltyDiscount(Integer loyaltyPointsUsed, BigDecimal subtotal) {
        if (loyaltyPointsUsed == null || loyaltyPointsUsed <= 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal discount = LOYALTY_POINT_VALUE.multiply(BigDecimal.valueOf(loyaltyPointsUsed));

        if (discount.compareTo(subtotal) > 0) {
            return subtotal;
        }

        return discount;
    }

    private Set<Long> normalizeIds(List<Long> ids) {
        if (ids == null) {
            return new LinkedHashSet<>();
        }
        return new LinkedHashSet<>(ids);
    }
}