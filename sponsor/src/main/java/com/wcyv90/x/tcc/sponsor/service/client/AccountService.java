package com.wcyv90.x.tcc.sponsor.service.client;

import com.wcyv90.x.tcc.sponsor.domain.dto.PayAccountInfoDTO;
import com.wcyv90.x.tcc.tx.feign.TccFeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "accountService",
        configuration = TccFeignConfiguration.class,
        url = "http://${account.svc.url:localhost}:${account.svc.port:8083}/account/"
)
public interface AccountService {

    @PutMapping("/pay/try")
    void tryPay(@RequestBody PayAccountInfoDTO payAccountInfoDTO);

    @PutMapping("/pay/confirm")
    void confirmPay(@RequestBody PayAccountInfoDTO payAccountInfoDTO);

    @PutMapping("/pay/cancel")
    void cancelPay(@RequestBody PayAccountInfoDTO payAccountInfoDTO);

}
