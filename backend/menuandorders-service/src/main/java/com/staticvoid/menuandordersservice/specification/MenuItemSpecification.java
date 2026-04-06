package com.staticvoid.menuandordersservice.specification;

import com.staticvoid.menuandordersservice.dto.MenuItemFilterDto;
import com.staticvoid.menuandordersservice.model.MenuItem;
import org.springframework.data.jpa.domain.Specification;

public class MenuItemSpecification {

    private MenuItemSpecification() {
    }

    public static Specification<MenuItem> filterBy(MenuItemFilterDto filter) {
        return Specification.<MenuItem>unrestricted()
                .and(nameContains(filter.getName()))
                .and(hasType(filter.getType()))
                .and(hasSeason(filter.getSeason()))
                .and(hasAvailability(filter.getAvailable()))
                .and(hasIngredientId(filter.getIngredientId()));
    }

    public static Specification<MenuItem> nameContains(String name) {
        return (root, query, cb) -> {
            if (name == null || name.trim().isEmpty()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("name")), "%" + name.trim().toLowerCase() + "%");
        };
    }

    public static Specification<MenuItem> hasType(Object type) {
        return (root, query, cb) -> {
            if (type == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("type"), type);
        };
    }

    public static Specification<MenuItem> hasSeason(Object season) {
        return (root, query, cb) -> {
            if (season == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("season"), season);
        };
    }

    public static Specification<MenuItem> hasAvailability(Boolean available) {
        return (root, query, cb) -> {
            if (available == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("available"), available);
        };
    }

    public static Specification<MenuItem> hasIngredientId(Long ingredientId) {
        return (root, query, cb) -> {
            if (ingredientId == null) {
                return cb.conjunction();
            }

            query.distinct(true);

            return cb.equal(
                    root.join("allowedIngredients")
                            .join("ingredient")
                            .get("id"),
                    ingredientId
            );
        };
    }
}