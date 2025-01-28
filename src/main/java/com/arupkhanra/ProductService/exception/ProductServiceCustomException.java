package com.arupkhanra.ProductService.exception;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductServiceCustomException extends RuntimeException {

    private String errorCode;

    public ProductServiceCustomException(String massage, String errorCode){
        super(massage);
        this.errorCode = errorCode;
    }

}
