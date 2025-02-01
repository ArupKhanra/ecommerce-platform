package com.arupkhanra.orderService.config;

import com.arupkhanra.orderService.external.client.decoder.CustomErrorDecoder;
import feign.codec.ErrorDecoder;
import lombok.Builder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    ErrorDecoder errorDecoder(){
        return new CustomErrorDecoder();
    }
}
