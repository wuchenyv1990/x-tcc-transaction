package com.wcyv90.x.tcc.account.controller;

import com.wcyv90.x.tcc.account.domain.dto.PayAccountInfoDTO;
import com.wcyv90.x.tcc.account.domain.service.AccountService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/account")
@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PutMapping("/pay/try")
    public void pay(@RequestBody PayAccountInfoDTO payAccountInfoDTO) {
        accountService.tryPay(payAccountInfoDTO.to());
    }

    @PutMapping("/pay/confirm")
    public void confirmPay(@RequestBody PayAccountInfoDTO payAccountInfoDTO) {
        accountService.confirmPay(payAccountInfoDTO.to());
    }

    @PutMapping("/pay/cancel")
    public void cancelPay(@RequestBody PayAccountInfoDTO payAccountInfoDTO) {
        accountService.cancelPay(payAccountInfoDTO.to());
    }

}
