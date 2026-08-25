package com.unir.orders.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ProductResponse {

    private Long id;

    private String nombre;

    private String descripcion;

    private double precio;

    private int cantidad;

    private String urlImagen;

    private String bodega;

    private Boolean visible;

    private CategoryResponse category;

}
