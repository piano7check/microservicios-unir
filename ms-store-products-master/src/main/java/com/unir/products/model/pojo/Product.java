package com.unir.products.model.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "productos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "precio")
	private double precio;

	@Column(name = "cantidad")
	private int cantidad;

	@Column(name = "url_imagen")
	private String urlImagen;

	@Column(name = "bodega")
	private String bodega;

	@Column(name = "visible")
	private Boolean visible;
  
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "id_categoria")
  private Category category;

}
