package com.wcyv90.x.tcc.order.controller;

import com.wcyv90.x.tcc.order.domain.dto.PayOrderInfoDTO;
import com.wcyv90.x.tcc.order.domain.service.OrderService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/order")
@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PutMapping("/pay/try")
    public void pay(@RequestBody PayOrderInfoDTO payOrderInfoDTO) {
        orderService.tryPayOrder(payOrderInfoDTO.to());
    }

    @PutMapping("/pay/confirm")
    public void confirmPay(@RequestBody PayOrderInfoDTO payOrderInfoDTO) {
        orderService.confirmPayOrder(payOrderInfoDTO.to());
    }

    @PutMapping("/pay/cancel")
    public void cancelPay(@RequestBody PayOrderInfoDTO payOrderInfoDTO) {
        orderService.cancelPayOrder(payOrderInfoDTO.to());
    }
}
