package com.wcyv90.x.tcc.tx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.wcyv90.x.tcc.tx")
@MapperScan("com.wcyv90.x.tcc.tx.db.mapper")
public class TccConfig {
}
