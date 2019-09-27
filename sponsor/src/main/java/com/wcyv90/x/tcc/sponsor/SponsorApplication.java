package com.wcyv90.x.tcc.sponsor;

import com.wcyv90.x.tcc.tx.core.recovery.EnableRecoveryTccJob;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.wcyv90.x.tcc.sponsor.service.client")
@EnableRecoveryTccJob
public class SponsorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SponsorApplication.class, args);
    }

}
