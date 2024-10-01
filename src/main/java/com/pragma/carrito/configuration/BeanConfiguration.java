package com.pragma.carrito.configuration;

import com.pragma.carrito.adapters.driven.feigns.adapter.ReportFeignClientAdapter;
import com.pragma.carrito.adapters.driven.feigns.adapter.StockFeignClientAdapter;
import com.pragma.carrito.adapters.driven.feigns.adapter.TransactionalFeignClientAdapter;
import com.pragma.carrito.adapters.driven.feigns.adapter.UserFeignClientAdapter;
import com.pragma.carrito.adapters.driven.feigns.clients.ReportFeignClient;
import com.pragma.carrito.adapters.driven.feigns.clients.StockFeignClient;
import com.pragma.carrito.adapters.driven.feigns.clients.TransactionalFeignClient;
import com.pragma.carrito.adapters.driven.feigns.clients.UserFeignClient;
import com.pragma.carrito.adapters.driven.jpa.mysql.adapter.CartAdapter;
import com.pragma.carrito.adapters.driven.jpa.mysql.mapper.ICartArticleEntityMapper;
import com.pragma.carrito.adapters.driven.jpa.mysql.repository.CartArticleRepository;
import com.pragma.carrito.adapters.driven.jpa.mysql.repository.CartRepository;
import com.pragma.carrito.adapters.securityconfig.SecurityContextPortImpl;
import com.pragma.carrito.domain.api.ICartServicePort;
import com.pragma.carrito.domain.spi.*;
import com.pragma.carrito.domain.usecases.CartUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {


    private final StockFeignClient stockFeignClient;
    private final UserFeignClient userFeignClient;
    private final TransactionalFeignClient transactionalFeignClient;
    private final CartArticleRepository cartArticleRepository;
    private final ICartArticleEntityMapper cartArticleEntityMapper;
    private final CartRepository cartRepository;
    private final ReportFeignClient reportFeignClient;

@Bean
public ReportFeignClientPort reportFeignClientPort() {
        return new ReportFeignClientAdapter(reportFeignClient);
    }

    @Bean
    public StockFeignClientPort stockFeignClientPort() {
        return new StockFeignClientAdapter(stockFeignClient);
    }
    @Bean
    public UserFeignClientPort userFeignClientPort() {
        return new UserFeignClientAdapter(userFeignClient);
    }
    @Bean
    public TransactionalFeignClientPort transactionalFeignClientPort() {
        return new TransactionalFeignClientAdapter(transactionalFeignClient);
    }
    @Bean
    public CartPersistencePort cartPersistencePort() {
        return new CartAdapter(cartArticleRepository, cartArticleEntityMapper, cartRepository);
    }
@Bean
    public ICartServicePort cartServicePort(){
        return new CartUseCase(stockFeignClientPort(), transactionalFeignClientPort(), cartPersistencePort(), securityContextPort(), reportFeignClientPort());
}
@Bean
    public ISecurityContextPort securityContextPort(){
        return new SecurityContextPortImpl();
    }


}

