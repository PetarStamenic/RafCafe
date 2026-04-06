package com.staticvoid.menuandordersservice.repository;

import com.staticvoid.menuandordersservice.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long>, JpaSpecificationExecutor<MenuItem> {

    Optional<MenuItem> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);
}