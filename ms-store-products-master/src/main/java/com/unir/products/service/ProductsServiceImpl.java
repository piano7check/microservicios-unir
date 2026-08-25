package com.unir.products.service;

import com.unir.products.data.CategoryRepository;
import com.unir.products.data.ProductRepository;
import com.unir.products.model.pojo.Category;
import com.unir.products.model.pojo.Product;
import com.unir.products.model.request.CreateProductRequest;
import com.unir.products.model.request.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ProductsServiceImpl implements ProductsService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private CategoryRepository catRepository;

    @Override
    public Product getProduct(String productId) {
        Product product = repository.findById(Long.valueOf(productId)).orElse(null);
        if (product != null && product.getVisible()) {
            return product;
        }
        return null;
    }

    @Override
    public Boolean removeProduct(String productId) {
        Product product = repository.findById(Long.valueOf(productId)).orElse(null);

        if (product != null) {
            product.setVisible(Boolean.FALSE);
            repository.save(product);
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    @Override
    public Product createProduct(CreateProductRequest request) {
        if (isValidCreateProductRequest(request)) {
            Category category = catRepository.findById(request.getIdCategoria()).orElse(null);
            Product product = buildProductFromRequest(request, category);
            return repository.save(product);
        } else {
            return null;
        }
    }

    @Override
    public List<Product> searchProducts(String nombre, String descripcion, String bodega, Double precio) {
        if (precio == null) {
            return repository.findByNombreContainingIgnoreCaseAndDescripcionContainingIgnoreCaseAndBodegaContainingIgnoreCaseAndVisibleTrue(nombre, descripcion, bodega);
        } else {
            return repository.findByNombreContainingIgnoreCaseAndDescripcionContainingIgnoreCaseAndBodegaContainingIgnoreCaseAndPrecioEqualsAndVisibleTrue(nombre, descripcion, bodega, precio);
        }
    }

    @Override
    public Boolean updateProduct(String productId, ProductRequest request) {
        Product product = repository.findById(Long.valueOf(productId)).orElse(null);
        if (product != null) {

            Boolean resp = Boolean.TRUE;
            if (request.getMotivo().equals("COMPRA")) {
                if (product.getCantidad() >= request.getCantidad()) {
                    int cantidad = product.getCantidad() - request.getCantidad();
                    product.setCantidad(cantidad);
                    repository.save(product);
                    resp = Boolean.TRUE;
                } else {
                    resp = Boolean.FALSE;
                }
            } else if (request.getMotivo().equals("DEVOLUCION")) {
                int cantidad = product.getCantidad() + request.getCantidad();
                product.setCantidad(cantidad);
                repository.save(product);
                resp = Boolean.TRUE;
            }
            return resp;
        } else {
            return Boolean.FALSE;
        }
    }

    private boolean isValidCreateProductRequest(CreateProductRequest request) {
        return request != null &&
                StringUtils.hasLength(request.getNombre().trim()) &&
                StringUtils.hasLength(request.getDescripcion().trim()) &&
                request.getPrecio() > 0 &&
                request.getCantidad() > 0 &&
                StringUtils.hasLength(request.getUrlImagen().trim()) &&
                StringUtils.hasLength(request.getBodega().trim()) &&
                request.getVisible() != null;
    }

    private Product buildProductFromRequest(CreateProductRequest request, Category category) {
        return Product.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .precio(request.getPrecio())
                .cantidad(request.getCantidad())
                .urlImagen(request.getUrlImagen())
                .bodega(request.getBodega())
                .visible(request.getVisible())
                .category(category)
                .build();
    }

    @Override
    public Product updateOneProduct(Integer id, CreateProductRequest request) {
        Category category = catRepository.findById(request.getIdCategoria()).orElse(null);
        Product product = repository.findById(Long.valueOf(id)).orElse(null);
        product.setNombre(request.getNombre());
        product.setDescripcion(request.getDescripcion());
        product.setPrecio(request.getPrecio());
        product.setCantidad(request.getCantidad());
        product.setUrlImagen(request.getUrlImagen());
        product.setBodega(request.getBodega());
        product.setCategory(category);
        return repository.save(product);
    }


}
