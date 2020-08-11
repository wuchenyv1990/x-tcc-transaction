package com.wcyv90.x.tcc.tx.hystrix;

import com.netflix.hystrix.strategy.HystrixPlugins;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(HystrixPlugins.class)
@ConfigurationProperties("feign.hystrix.enabled")
public class HystrixConfig {

    @Bean
    public RequestContextConcurrencyStrategy requestContextConcurrencyStrategy() {
        return new RequestContextConcurrencyStrategy();
    }

}
