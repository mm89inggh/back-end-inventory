package com.unir.inventory.resources.mapper;

import com.unir.inventory.resources.entity.Producto;
import com.unir.inventory.resources.entity.ProductoDto;
import com.unir.inventory.resources.model.InventoryRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductoMapper {

        @Mapping(source = "name", target = "nombre")
        @Mapping(source = "quantity", target = "cantidad")
        @Mapping(source = "sellingPrice", target = "precioCompra")
        @Mapping(source = "purchasePrice", target = "precioVenta")
        Producto toProduct(InventoryRequest request);

        @Mapping(source = "nombre", target = "name")
        @Mapping(source = "cantidad", target = "quantity")
        @Mapping(source = "precioCompra", target = "sellingPrice")
        @Mapping(source = "precioVenta", target = "purchasePrice")
        @Mapping(source = "id", target = "id")
        ProductoDto toProductoDto(Producto producto);

        List<ProductoDto> toProductosDto(List<Producto> productos);


}
