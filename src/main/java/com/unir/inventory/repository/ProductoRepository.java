package com.unir.inventory.repository;

import com.unir.inventory.repository.filter.ProductoFilter;
import com.unir.inventory.resources.entity.Producto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductoRepository {

    private final ProductoJpaRepository productoJpaRepository;

    public Producto save(Producto producto) {
        return productoJpaRepository.save(producto);
    }

    public boolean existsById(Long id) {
        return productoJpaRepository.existsById(id);
    }

    public Producto findById(Long id) {
        return productoJpaRepository.findById(id).orElse(null);
    }

    public List<Producto> search(String name, Integer quality, BigDecimal price) {
        ProductoFilter spec = new ProductoFilter(name, quality, price);
        return productoJpaRepository.findAll(spec);
    }

    public void delete(Long id){
        productoJpaRepository.deleteById(id);
    }
}
