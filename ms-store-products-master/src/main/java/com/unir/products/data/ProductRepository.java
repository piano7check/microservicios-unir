package com.unir.products.data;

import com.unir.products.model.pojo.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByNombre(String nombre);

	List<Product> findByVisibleTrue();

	List<Product> findByVisibleTrueAndNombreContainingIgnoreCase(String keyword);

	List<Product> findByNombreContainingIgnoreCaseAndDescripcionContainingIgnoreCaseAndBodegaContainingIgnoreCaseAndVisibleTrue(String nombre, String descripcion, String bodega);

	List<Product> findByNombreContainingIgnoreCaseAndDescripcionContainingIgnoreCaseAndBodegaContainingIgnoreCaseAndPrecioEqualsAndVisibleTrue(String nombre, String descripcion, String bodega, Double precio);

}