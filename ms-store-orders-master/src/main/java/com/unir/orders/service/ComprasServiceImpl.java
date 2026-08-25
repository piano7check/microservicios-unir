package com.unir.orders.service;

import com.unir.orders.data.CompraDetalleRepository;
import com.unir.orders.data.CompraRepository;
import com.unir.orders.facade.ProductsFacade;
import com.unir.orders.model.pojo.Compra;
import com.unir.orders.model.pojo.CompraDetalle;
import com.unir.orders.model.request.*;
import com.unir.orders.model.response.CompraProductsResponse;
import com.unir.orders.model.response.CompraResponse;
import com.unir.orders.model.response.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class ComprasServiceImpl implements ComprasService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private CompraDetalleRepository compraDetalleRepository;

    @Autowired
    private ProductsFacade productsFacade;


    public List<CompraProductsResponse> getCompras() {
        List<CompraProductsResponse> listCompResp = new ArrayList<>();
        List<Compra> listComp = compraRepository.findAll();
        if (listComp != null) {
            for (Compra comp : listComp) {
                CompraProductsResponse compraProdResp = getCompra(comp.getId().toString());
                listCompResp.add(compraProdResp);
            }
        }
        return listCompResp;
    }

    @Override
    public CompraProductsResponse getCompra(String compraId) {
        CompraProductsResponse compProdRes = new CompraProductsResponse();
        Compra compra = compraRepository.findById(Long.valueOf(compraId)).orElse(null);
        if (compra != null) {
            compProdRes.setId(compra.getId());
            compProdRes.setFechaCompra(compra.getFechaCompra());
            compProdRes.setEstado(compra.getEstado());
            compProdRes.setCodigoCompra(compra.getCodigoCompra());

            List<CompraDetalle> compDetalle = compra.getCompraDetalles();
            List<ProductResponse> listProducts = new ArrayList<>();
            for (CompraDetalle compraDetalle : compDetalle) {
                ProductResponse product = productsFacade.getProduct(compraDetalle.getIdProducto().toString());
                product.setId(compraDetalle.getIdProducto());
                product.setPrecio(compraDetalle.getPrecio());
                product.setCantidad(compraDetalle.getCantidad());
                listProducts.add(product);
            }
            compProdRes.setProducts(listProducts);
        }

        return compProdRes;
    }

    @Override
    public Compra createOrder(CompraRequest request) {

        // sevalida que existan los productos para generar la compra
        Boolean compraValida = true;
        for (CompraDetalleRequest compraDetalleRequest : request.getProducts()) {
            ProductResponse product = productsFacade.getProduct(compraDetalleRequest.getId().toString());
            if ( product == null ) {
                compraValida = false;
            }
        }

        Compra compra = new Compra();
        List<CompraDetalle> listCompraDetalle = new ArrayList<>();

        if (compraValida == true) {

            String guid = UUID.randomUUID().toString();
            compra.setCodigoCompra(guid);
            LocalDate currentDate = LocalDate.now();
            compra.setFechaCompra(currentDate);
            compra.setEstado("OK");
            compraRepository.save(compra);

            for (CompraDetalleRequest compraDetalleRequest : request.getProducts()) {
                ProductResponse product = productsFacade.getProduct(compraDetalleRequest.getId().toString());
                CompraDetalle compDet = new CompraDetalle();
                compDet.setCompra(compra);
                compDet.setCantidad(compraDetalleRequest.getCantidad());
                compDet.setIdProducto(compraDetalleRequest.getId());
                compDet.setPrecio(product.getPrecio());

                try {
                    ProductRequest productRequest = new ProductRequest();
                    productRequest.setCantidad(compDet.getCantidad());
                    productRequest.setMotivo("COMPRA");
                    Boolean response = productsFacade.updateCountProduct(String.valueOf(compDet.getIdProducto()), productRequest);
                    if (response) {
                        compDet.setEstado("COMPRADO");
                    } else {
                        compDet.setEstado("SIN_STOCK");
                    }
                } catch (Exception e) {
                    compDet.setEstado("SIN_STOCK");
                }
                compraDetalleRepository.save(compDet);
                listCompraDetalle.add(compDet);
            }

            compra.setCompraDetalles(listCompraDetalle);
            compraRepository.save(compra);
            return compra;
        } else {
            return compra;
        }

    }
//    @Override
//    public Boolean removeCompra(String compraId) {
//
//        Compra compra = compraRepository.findById(Long.valueOf(compraId)).orElse(null);
//
//        if (compra != null) {
//            compraRepository.delete(compra);
//            return Boolean.TRUE;
//        } else {
//            return Boolean.FALSE;
//        }
//    }

    @Override
    public Compra partiallyUpdateCompra(CompraPatchRequest compraPatchRequest, String compraId) {

        Compra compra = compraRepository.findById(Long.valueOf(compraId)).orElse(null);

        if (compra != null) {
            for (CompraDetalle compraDetalle : compra.getCompraDetalles()) {
                if (compraDetalle.getEstado().equals("SIN_STOCK"))
                    continue;

                ProductRequest productRequest = new ProductRequest();
                productRequest.setCantidad(compraDetalle.getCantidad());
                productRequest.setMotivo("DEVOLUCION");
                productsFacade.updateCountProduct(String.valueOf(compraDetalle.getIdProducto()), productRequest);
            }
            compra.setEstado(compraPatchRequest.getEstado());
            compraRepository.save(compra);
            return compra;
        }

        return null;
    }

}
