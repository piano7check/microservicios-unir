package com.unir.orders.data;

import com.unir.orders.model.pojo.CompraDetalle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompraDetalleRepository extends JpaRepository<CompraDetalle, Long> {

}
