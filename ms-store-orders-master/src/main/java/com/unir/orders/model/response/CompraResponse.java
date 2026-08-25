package com.unir.orders.model.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CompraResponse {

    private Long id;

    private String codigoCompra;

    private LocalDate fechaCompra;

    private String estado;

}
