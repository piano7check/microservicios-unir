package com.unir.orders.model.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "compra")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String codigoCompra;

    @Column(nullable = false)
    private LocalDate fechaCompra;

    @Column(nullable = false)
    private String estado;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL)
    private List<CompraDetalle> compraDetalles;

}
