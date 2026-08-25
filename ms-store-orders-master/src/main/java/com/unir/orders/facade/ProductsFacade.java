package com.unir.orders.facade;

import com.unir.orders.model.request.ProductRequest;
import com.unir.orders.model.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductsFacade {

    @Value("${getProduct.url}")
    private String getProductUrl;

    private final RestTemplate restTemplate;


    public ProductResponse getProduct(String id) {
        try {
            String url = String.format(getProductUrl, id);
            return restTemplate.getForObject(url, ProductResponse.class);
        } catch (HttpClientErrorException e) {
            log.error("Client Error: {}, Product with ID {}", e.getStatusCode(), id);
            return null;
        }
    }

    public boolean updateCountProduct(String id, ProductRequest productRequest) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<ProductRequest> requestEntity = new HttpEntity<>(productRequest, headers);

            ResponseEntity<Void> responseEntity = restTemplate.exchange(
                    String.format(getProductUrl, id),
                    HttpMethod.PATCH,
                    requestEntity,
                    Void.class
            );

            return responseEntity.getStatusCode() == HttpStatus.OK;
        } catch (HttpClientErrorException e) {
            log.error("Client Error: {}, Product with ID {}", e.getStatusCode(), id);
            return false;
        }
    }
}
