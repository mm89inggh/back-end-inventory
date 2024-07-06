package com.unir.inventory.repository;

import com.unir.inventory.resources.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductoJpaRepository extends JpaRepository<Producto, Long>, JpaSpecificationExecutor<Producto> {


}