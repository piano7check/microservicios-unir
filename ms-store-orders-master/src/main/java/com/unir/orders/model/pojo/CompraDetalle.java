package com.unir.orders.model.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "compra_detalle")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CompraDetalle {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "id_producto")
	private Long idProducto;

	@Column(name = "cantidad")
	private Integer cantidad;

	@Column(name = "precio")
	private Double precio;

	@Column(name = "estado")
	private String estado;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "compra_id")
	private Compra compra;

}
