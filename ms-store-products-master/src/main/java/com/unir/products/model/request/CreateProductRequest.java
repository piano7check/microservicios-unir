package com.unir.products.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {

	private String nombre;
	private String descripcion;
	private double precio;
	private int cantidad;
	private String urlImagen;
	private String bodega;
	private Boolean visible;
	private int idCategoria;
}
