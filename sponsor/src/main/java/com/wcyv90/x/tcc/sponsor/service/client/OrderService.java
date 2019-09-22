package com.wcyv90.x.tcc.sponsor.service.client;

import com.wcyv90.x.tcc.sponsor.domain.dto.PayOrderInfoDTO;
import com.wcyv90.x.tcc.tx.feign.TccFeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "orderService",
        configuration = TccFeignConfiguration.class,
        url = "http://${order.svc.url:localhost}:${order.svc.port:8082}/order/"
)
public interface OrderService {

    @PutMapping("/pay/try")
    void tryPay(@RequestBody PayOrderInfoDTO payOrderInfoDTO);

    @PutMapping("/pay/confirm")
    void confirmPay(@RequestBody PayOrderInfoDTO payOrderInfoDTO);

    @PutMapping("/pay/cancel")
    void cancelPay(@RequestBody PayOrderInfoDTO payOrderInfoDTO);

}
