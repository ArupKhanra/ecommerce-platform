package com.arupkhanra.ProductService.service;

import com.arupkhanra.ProductService.entiry.Product;
import com.arupkhanra.ProductService.exception.ProductServiceCustomException;
import com.arupkhanra.ProductService.model.ProductRequest;
import com.arupkhanra.ProductService.model.ProductResponse;
import com.arupkhanra.ProductService.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.beans.BeanUtils.*;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;
    @Override
    public Long addProduct(ProductRequest productRequest) {
        log.info("adding product..");
        Product product
                = Product.builder()
                 .productName(productRequest.getName())
                 .price(productRequest.getPrice())
                 .quantity(productRequest.getQuantity())
                 .build();
        productRepository.save(product);
        log.info("product created : "+product.getProductId());
        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(long productId) {

        log.info("get the product for productId : {}", productId);
        Product product
                = productRepository.findById(productId).orElseThrow(
                        ()->new ProductServiceCustomException("Product with given id not found","PRODUCT_NOT_FOUND"));

        ProductResponse productResponse = new ProductResponse();
        copyProperties(product,productResponse);
        return productResponse;
    }

    @Override
    public void reduceQuantity(long productId, long quantity) {
        log.info("reduce quantity {} for id {}",quantity,productId);
        Product product =
                productRepository.findById(productId).orElseThrow(()->new ProductServiceCustomException(
                        "product with given Id not found","PRODUCT_NOT_FOUND"
                ));
        if(product.getQuantity() < quantity){
            throw new ProductServiceCustomException("product has not have sufficient quantity",
                    "INSUFFICIENT_QUANTITY");
        }
        product.setQuantity(product.getQuantity()-quantity);
        productRepository.save(product);
        log.info("product updated Successfully");
    }
}
