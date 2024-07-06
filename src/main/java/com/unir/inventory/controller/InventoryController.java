package com.unir.inventory.controller;

import com.unir.inventory.resources.entity.ProductoDto;
import com.unir.inventory.resources.model.ApisResponse;
import com.unir.inventory.resources.model.InventoryRequest;
import com.unir.inventory.resources.model.InventoryResponse;
import com.unir.inventory.service.InventoryService;
import com.unir.inventory.util.ApiResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@RestController
@Description("Inventory API")
@Tag(name = "inventory")
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @Operation(summary = "Crea producto", description = "Crea un producto con sus detalles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApisResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Not found", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApisResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal error", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApisResponse.class))})
    })
    @PostMapping("/products")
    public ResponseEntity<ApisResponse<InventoryResponse>> create(@Valid  @RequestBody InventoryRequest inventoryRequest) {
        return ApiResponseUtil.created(new InventoryResponse(inventoryService.create(inventoryRequest)));
    }

    @Operation(summary = "Actualiza producto", description = "Actualiza un producto completamente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApisResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Not found", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApisResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal error", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApisResponse.class))})
    })
    @PutMapping("/products/{id}")
    public ResponseEntity<ApisResponse<InventoryResponse>> update(@PathVariable Long id, @RequestBody InventoryRequest inventoryRequest) {
        if (inventoryService.update(id, inventoryRequest)) {
            return ApiResponseUtil.ok(new InventoryResponse(id));
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Muestra productos", description = "Muestra productos mediante filtros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApisResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Not found", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApisResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal error", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApisResponse.class))})
    })
    @GetMapping("/products")
    public ResponseEntity<ApisResponse<List<ProductoDto>>> get(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "quantity", required = false) Integer quantity,
            @RequestParam(name = "price", required = false) BigDecimal price) {
        List<ProductoDto> productos = inventoryService.search(name, quantity, price);
        if (productos != null && !productos.isEmpty()) {
            return ApiResponseUtil.ok(productos);
        }
        return ApiResponseUtil.ok(Collections.emptyList());

    }

    @Operation(summary = "Muestra producto unico", description = "Muestra un producto filtrado por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApisResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Not found", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApisResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal error", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApisResponse.class))})
    })
    @GetMapping("/products/{id}")
    public ResponseEntity<ApisResponse<ProductoDto>> get(@PathVariable Long id) {
        ProductoDto producto = inventoryService.findById(id);
        if (producto != null) {
            return ApiResponseUtil.ok(producto);
        }
        return ResponseEntity.notFound().build();

    }

    @Operation(summary = "Elimina producto", description = "Elimina un producto por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApisResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Not found", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApisResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal error", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApisResponse.class))})
    })
    @DeleteMapping("/products/{id}")
    public ResponseEntity<ApisResponse<String>> delete(@PathVariable Long id) {
        boolean deleted = inventoryService.delete(id);
        if (deleted) {
            return ApiResponseUtil.ok("Eliminado");
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Modificado parcial", description = "Modificacion parcial del producto",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del producto a crear.",
                    required = true,
                    content = @Content(mediaType = "application/merge-patch+json", schema = @Schema(implementation = String.class))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApisResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Not found", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApisResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal error", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApisResponse.class))})
    })
    @PatchMapping("/products/{id}")
    public ResponseEntity<ApisResponse<ProductoDto>> patch(@PathVariable Long id, @RequestBody String patchBody ) {
        ProductoDto patched = inventoryService.update(id, patchBody);
        if (patched != null) {
            return ApiResponseUtil.ok(patched);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

}
