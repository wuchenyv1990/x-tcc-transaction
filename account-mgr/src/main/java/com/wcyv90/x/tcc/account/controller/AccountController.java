package com.wcyv90.x.tcc.account.controller;

import com.wcyv90.x.tcc.account.domain.dto.PayAccountInfoDTO;
import com.wcyv90.x.tcc.account.domain.service.AccountManager;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/account")
@RestController
public class AccountController {

    private final AccountManager accountManager;

    public AccountController(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    @PutMapping("/pay/try")
    public void pay(@RequestBody PayAccountInfoDTO payAccountInfoDTO) {
        accountManager.tryPay(payAccountInfoDTO.to());
    }

    @PutMapping("/pay/confirm")
    public void confirmPay(@RequestBody PayAccountInfoDTO payAccountInfoDTO) {
        accountManager.confirmPay(payAccountInfoDTO.to());
    }

    @PutMapping("/pay/cancel")
    public void cancelPay(@RequestBody PayAccountInfoDTO payAccountInfoDTO) {
        accountManager.cancelPay(payAccountInfoDTO.to());
    }

}
