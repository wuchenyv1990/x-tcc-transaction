package com.wcyv90.x.tcc.order.controller;

import com.wcyv90.x.tcc.order.domain.dto.PayInfoDTO;
import com.wcyv90.x.tcc.order.domain.service.OrderManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    OrderManager orderManager;

    @PutMapping("/pay")
    public void pay(PayInfoDTO payInfoDTO) {
        orderManager.payOrder(payInfoDTO.to());
    }

}
