package com.unir.inventory.resources.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDto {

    private Long id;
    private String name;
    private Integer quantity;
    private BigDecimal sellingPrice;
    private BigDecimal PurchasePrice;
}
