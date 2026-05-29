package com.staticvoid.menuandordersservice.specification;

import com.staticvoid.menuandordersservice.dto.filters.OrderFilterDto;
import com.staticvoid.menuandordersservice.model.Order;
import org.springframework.data.jpa.domain.Specification;

public class OrderSpecification {

    private OrderSpecification() {
    }

    public static Specification<Order> filterBy(OrderFilterDto filter) {
        return Specification.<Order>unrestricted()
                .and(hasCustomerId(filter.getCustomerId()))
                .and(hasStatus(filter.getStatus()));
    }

    public static Specification<Order> hasCustomerId(Long customerId) {
        return (root, query, cb) -> {
            if (customerId == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("customerId"), customerId);
        };
    }

    public static Specification<Order> hasStatus(Object status) {
        return (root, query, cb) -> {
            if (status == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("status"), status);
        };
    }
}