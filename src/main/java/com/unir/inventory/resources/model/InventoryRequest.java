package com.unir.inventory.resources.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InventoryRequest {

    @NotNull
    @NotBlank
    private String name;
    @NotNull
    private Integer quantity;
    @NotNull
    private BigDecimal sellingPrice;
    @NotNull
    private BigDecimal PurchasePrice;

}
