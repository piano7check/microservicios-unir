package com.unir.products.controller;

import com.unir.products.model.pojo.Product;
import com.unir.products.model.request.CreateProductRequest;
import com.unir.products.model.request.ProductRequest;
import com.unir.products.service.ProductsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductsController {

	private final ProductsService service;

	@GetMapping("/products/{productId}")
	public ResponseEntity<Product> getProduct(@PathVariable String productId) {
		log.info("Request received for product {}", productId);

		Product product = service.getProduct(productId);
		return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/products/{productId}")
	public ResponseEntity<Void> deleteProduct(@PathVariable String productId) {
		Boolean removed = service.removeProduct(productId);
		return Boolean.TRUE.equals(removed) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
	}

	@PostMapping("/products")
	public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest request) {
		Product createdProduct = service.createProduct(request);
		return createdProduct != null ? ResponseEntity.status(HttpStatus.CREATED).body(createdProduct)
				: ResponseEntity.badRequest().build();
	}

	@GetMapping("/products")
	public ResponseEntity<List<Product>> searchProducts(@RequestParam(required = false) String nombre,
														@RequestParam(required = false) String descripcion,
														@RequestParam(required = false) Double precio,
														@RequestParam(required = false) String bodega) {
		log.info("Search request received with keyword: {}", nombre);
		if (nombre == null) {
			nombre = "";
		}
		if (descripcion == null) {
			descripcion = "";
		}
		if (bodega == null) {
			bodega = "";
		}
		List<Product> products = service.searchProducts(nombre, descripcion, bodega, precio);
		return ResponseEntity.ok(products != null ? products : Collections.emptyList());
	}

	@PatchMapping("/products/{productId}")
	public ResponseEntity<Void> patchProduct(@PathVariable String productId, @RequestBody ProductRequest request) {
		Boolean update = service.updateProduct(productId, request);
		return Boolean.TRUE.equals(update) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
	}

	@PutMapping("/products/{productId}")
	public ResponseEntity<Product> updateProduct(@PathVariable Integer productId, @RequestBody CreateProductRequest request) {

		Product product = service.getProduct(String.valueOf(productId));

		if (product != null) {
			Product productUpdate = service.updateOneProduct(productId,request);
			return productUpdate != null ? ResponseEntity.ok(productUpdate) : ResponseEntity.badRequest().build();
		}
		else {
			return ResponseEntity.notFound().build();
		}

	}
}
