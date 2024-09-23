package com.pragma.carrito.adapters.driven.feigns;


import com.pragma.carrito.adapters.driven.feigns.interceptors.FeignClientInterceptor;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return new FeignClientInterceptor();
    }
    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }

}

