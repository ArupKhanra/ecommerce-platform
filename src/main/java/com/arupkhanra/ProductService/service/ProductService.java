package com.arupkhanra.ProductService.service;

import com.arupkhanra.ProductService.model.ProductRequest;
import com.arupkhanra.ProductService.model.ProductResponse;

public interface ProductService {
    Long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(long productId);

    void reduceQuantity(long productId, long quantity);
}
