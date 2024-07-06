package com.unir.inventory.repository.filter;

import com.unir.inventory.resources.entity.Producto;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
public class ProductoFilter implements Specification<Producto> {

    private final String nombre;
    private final Integer cantidad;
    private final BigDecimal precio;


    @Override
    public Predicate toPredicate(Root<Producto> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new LinkedList<>();
        if (StringUtils.isNotEmpty(nombre)) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("nombre")),
                    "%" + nombre.toLowerCase() + "%"));
        }
        if (cantidad != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("cantidad"), cantidad));
        }
        if (precio != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("precioVenta"), precio));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
