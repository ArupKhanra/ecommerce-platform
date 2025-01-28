package com.arupkhanra.ProductService.service;

import com.arupkhanra.ProductService.entiry.Product;
import com.arupkhanra.ProductService.model.ProductRequest;
import com.arupkhanra.ProductService.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;
    @Override
    public Long addProduct(ProductRequest productRequest) {
        log.info("adding product..");
        Product product
                =Product.builder()
                .productName(productRequest.getName())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .build();
        productRepository.save(product);
        log.info("product created : "+product.getProductId());
        return product.getProductId();
    }
}
