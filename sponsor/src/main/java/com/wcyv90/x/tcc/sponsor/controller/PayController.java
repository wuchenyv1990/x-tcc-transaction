package com.wcyv90.x.tcc.sponsor.controller;

import com.wcyv90.x.tcc.sponsor.domain.dto.PayInfoDTO;
import com.wcyv90.x.tcc.sponsor.domain.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayController {

    @Autowired
    PayService payService;

    @PutMapping("/pay")
    public boolean pay(@RequestBody PayInfoDTO payInfoDTO) {
        return payService.pay(payInfoDTO.to());

    }

}
