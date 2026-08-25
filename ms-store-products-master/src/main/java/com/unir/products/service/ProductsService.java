package com.unir.products.service;

import com.unir.products.model.pojo.Product;
import com.unir.products.model.request.CreateProductRequest;
import com.unir.products.model.request.ProductRequest;

import java.util.List;

public interface ProductsService {
	
	Product getProduct(String productId);
	
	Boolean removeProduct(String productId);
	
	Product createProduct(CreateProductRequest request);

	List<Product> searchProducts(String nombre, String descripcion, String bodega, Double precio);

	Boolean updateProduct(String productId,  ProductRequest request) ;

	Product updateOneProduct(Integer id, CreateProductRequest request);

}
