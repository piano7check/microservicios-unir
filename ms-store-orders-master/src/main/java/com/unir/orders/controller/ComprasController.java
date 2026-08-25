package com.unir.orders.controller;

import com.unir.orders.model.pojo.Compra;
import com.unir.orders.model.request.CompraPatchRequest;
import com.unir.orders.model.request.CompraRequest;
import com.unir.orders.model.request.CompraDetalleRequest;
import com.unir.orders.model.request.ProductRequest;
import com.unir.orders.model.response.CompraProductsResponse;
import com.unir.orders.model.response.CompraResponse;
import com.unir.orders.service.ComprasService;
import com.unir.orders.utils.Validators;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ComprasController {

    private final ComprasService service;

    @PostMapping("/compras")
    public ResponseEntity<Compra> createOrder(@RequestBody CompraRequest request) {
        try {
            Compra result = service.createOrder(request);
            return result.getId() != null ? ResponseEntity.status(HttpStatus.CREATED).body(result) : ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/compras/{compraId}")
    public ResponseEntity<CompraProductsResponse> getCompra(@PathVariable String compraId) {
        if (!Validators.esNumero(compraId))
            return ResponseEntity.badRequest().build();
        CompraProductsResponse compProdsRes = service.getCompra(compraId);
        return compProdsRes.getId() != null ? ResponseEntity.ok(compProdsRes) : ResponseEntity.notFound().build();
    }

    @GetMapping("/compras")
    public ResponseEntity<List<CompraProductsResponse>> getCompras() {
        List<CompraProductsResponse> compProdsRes = service.getCompras();
        return compProdsRes.isEmpty() ? ResponseEntity.ok(Collections.emptyList()) : ResponseEntity.ok(compProdsRes) ;
    }



    @PatchMapping("/compras/{compraId}")
    public ResponseEntity<Compra> partiallyUpdateCompra(@RequestBody CompraPatchRequest request, @PathVariable String compraId) {

        if (!Validators.esNumero(compraId))
            return ResponseEntity.badRequest().build();

        Compra result = service.partiallyUpdateCompra(request, compraId);

        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
