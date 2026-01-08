package com.ramona.capstone.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "variants")
@Getter
@Setter
@NoArgsConstructor
public class Variant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String color;
    private BigDecimal price;
    private String sku;
    private Integer quantity;
    private String fuelType;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
