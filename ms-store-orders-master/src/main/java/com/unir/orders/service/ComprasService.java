package com.unir.orders.service;

import com.unir.orders.model.pojo.Compra;
import com.unir.orders.model.request.CompraDetalleRequest;
import com.unir.orders.model.request.CompraPatchRequest;
import com.unir.orders.model.request.CompraRequest;
import com.unir.orders.model.request.ProductRequest;
import com.unir.orders.model.response.CompraProductsResponse;
import com.unir.orders.model.response.CompraResponse;

import java.util.List;
import java.util.Map;

public interface ComprasService {

    Compra createOrder(CompraRequest request);

    CompraProductsResponse getCompra(String compraId);

    List<CompraProductsResponse> getCompras();

//    Boolean removeCompra(String compraId);

    Compra partiallyUpdateCompra(CompraPatchRequest compraPatchRequest, String compraId);

}
