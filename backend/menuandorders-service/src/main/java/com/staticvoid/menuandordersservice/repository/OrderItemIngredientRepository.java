package com.staticvoid.menuandordersservice.repository;

import com.staticvoid.menuandordersservice.model.OrderItemIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemIngredientRepository extends JpaRepository<OrderItemIngredient, Long> {

    List<OrderItemIngredient> findByOrderItemId(Long orderItemId);
}