package com.wcyv90.x.tcc.tx.feign;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

    @Bean
    public TccContextInterceptor tccContextInterceptor() {
        return new TccContextInterceptor();
    }

}
