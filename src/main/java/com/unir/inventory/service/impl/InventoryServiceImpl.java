package com.unir.inventory.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import com.unir.inventory.repository.ProductoRepository;
import com.unir.inventory.resources.entity.Producto;
import com.unir.inventory.resources.entity.ProductoDto;
import com.unir.inventory.resources.mapper.ProductoMapper;
import com.unir.inventory.resources.model.InventoryRequest;
import com.unir.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;
    private final ObjectMapper objectMapper;

    @Override
    public Long create(InventoryRequest request) {
        Producto producto = productoMapper.toProduct(request);
        producto = productoRepository.save(producto);
        return producto.getId();

    }

    @Override
    public boolean update(Long id, InventoryRequest request) {
        boolean updated = false;
        if (productoRepository.existsById(id)) {
            Producto producto = productoMapper.toProduct(request);
            producto.setId(id);
            productoRepository.save(producto);
            updated = true;
        }
        return updated;
    }

    @Override
    public List<ProductoDto> search(String name, Integer quantity, BigDecimal price) {
        return productoMapper.toProductosDto(productoRepository.search(name, quantity, price));
    }

    @Override
    public ProductoDto findById(Long id) {
        return productoMapper.toProductoDto(productoRepository.findById(id));
    }

    @Override
    public boolean delete(Long id) {
        boolean deleted = false;
        if (productoRepository.existsById(id)) {
            productoRepository.delete(id);
            deleted = true;
        }
        return deleted;
    }

    @Override
    public ProductoDto update(Long productId, String updateRequest) {
        Producto product = productoRepository.findById(productId);
        if (product != null) {
            try {
                JsonMergePatch jsonMergePatch = JsonMergePatch.fromJson(objectMapper.readTree(parseObj(updateRequest)));
                JsonNode target = jsonMergePatch.apply(objectMapper.readTree(objectMapper.writeValueAsString(product)));
                Producto patched = objectMapper.treeToValue(target, Producto.class);
                productoRepository.save(patched);
                return productoMapper.toProductoDto(patched);
            } catch (JsonProcessingException | JsonPatchException e) {
                log.error("Error updating product {}", productId, e);
                return null;
            }
        } else {
            return null;
        }
    }

    private String parseObj(String udateRequest) throws JsonProcessingException {
        InventoryRequest request = objectMapper.readValue(udateRequest, InventoryRequest.class);
        Producto updateRequestObj = productoMapper.toProduct(request);
        return objectMapper.writeValueAsString(updateRequestObj);
    }
}
