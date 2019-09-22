package com.wcyv90.x.tcc.tx.feign;

import feign.RequestInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(RequestInterceptor.class)
public class TccFeignConfiguration {

    @Bean
    public TccContextInterceptor tccContextInterceptor() {
        return new TccContextInterceptor();
    }

}
