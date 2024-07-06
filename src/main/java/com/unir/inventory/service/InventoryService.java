package com.unir.inventory.service;

import com.unir.inventory.resources.entity.Producto;
import com.unir.inventory.resources.entity.ProductoDto;
import com.unir.inventory.resources.model.InventoryRequest;

import java.math.BigDecimal;
import java.util.List;

public interface InventoryService {

    Long create(InventoryRequest request);

    boolean update(Long id, InventoryRequest request);

    List<ProductoDto> search(String name, Integer quantity, BigDecimal price);

    ProductoDto findById (Long id);

    boolean delete (Long id);

    ProductoDto update(Long productId, String updateRequest);

}
