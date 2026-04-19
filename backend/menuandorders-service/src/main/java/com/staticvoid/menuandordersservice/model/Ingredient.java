package com.staticvoid.menuandordersservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(
        name = "ingredients",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name")
        }
)
@Getter
@Setter
@NoArgsConstructor
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal extraPrice;

    @Column(nullable = false)
    private boolean available = true;

    public Ingredient(String name, BigDecimal extraPrice, boolean available) {
        this.name = name;
        this.extraPrice = extraPrice;
        this.available = available;
    }
}