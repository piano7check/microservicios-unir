package com.unir.orders.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CompraProductsResponse extends CompraResponse {

    private List<ProductResponse> products;

}
