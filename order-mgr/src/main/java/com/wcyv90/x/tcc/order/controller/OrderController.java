package com.wcyv90.x.tcc.order.controller;

import com.wcyv90.x.tcc.order.domain.dto.PayOrderInfoDTO;
import com.wcyv90.x.tcc.order.domain.service.OrderManager;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/order")
@RestController
public class OrderController {

    private final OrderManager orderManager;

    public OrderController(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    @PutMapping("/pay/try")
    public void pay(PayOrderInfoDTO payOrderInfoDTO) {
        orderManager.tryPayOrder(payOrderInfoDTO.to());
    }

    @PutMapping("/pay/confirm")
    public void confirmPay(PayOrderInfoDTO payOrderInfoDTO) {
        orderManager.confirmPayOrder(payOrderInfoDTO.to());
    }

    @PutMapping("/pay/cancel")
    public void cancelPay(PayOrderInfoDTO payOrderInfoDTO) {
        orderManager.cancelPayOrder(payOrderInfoDTO.to());
    }
}
