package com.wcyv90.x.tcc.tx.core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;

import static com.wcyv90.x.tcc.tx.core.TccEnvFilter.TCC_FILTER_ORDER;

@Configuration
@ComponentScan("com.wcyv90.x.tcc.tx")
@EnableConfigurationProperties(TccProperty.class)
@MapperScan("com.wcyv90.x.tcc.tx.db.mapper")
public class TccConfig {

    @Bean
    public FilterRegistrationBean svcTraceFilter() {
        FilterRegistrationBean<TccEnvFilter> filterRegistrationBean = new FilterRegistrationBean<>(
                tccEnvFilter());
        filterRegistrationBean.setDispatcherTypes(DispatcherType.ASYNC,
                DispatcherType.ERROR, DispatcherType.FORWARD, DispatcherType.INCLUDE,
                DispatcherType.REQUEST);
        filterRegistrationBean.setOrder(TCC_FILTER_ORDER);
        return filterRegistrationBean;
    }

    @Bean
    public TccEnvFilter tccEnvFilter() {
        return new TccEnvFilter();
    }

}
