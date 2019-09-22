package com.wcyv90.x.tcc.sponsor.service;

import com.wcyv90.x.tcc.sponsor.domain.dto.PayAccountInfoDTO;
import com.wcyv90.x.tcc.sponsor.domain.dto.PayOrderInfoDTO;
import com.wcyv90.x.tcc.sponsor.domain.model.PayInfo;
import com.wcyv90.x.tcc.sponsor.domain.service.PayManager;
import com.wcyv90.x.tcc.sponsor.domain.service.PayService;
import com.wcyv90.x.tcc.sponsor.service.client.AccountService;
import com.wcyv90.x.tcc.sponsor.service.client.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl implements PayService {

    @Autowired
    PayManager payManager;

    @Autowired
    OrderService orderService;

    @Autowired
    AccountService accountService;

    /**
     *  try 主事务try成功才继续
     *
     * @param payInfo payInfo
     */
    @Override
    public void pay(PayInfo payInfo) {
        payManager.tryPay(payInfo); //主事务表：trying
        try {
            orderService.tryPay(PayOrderInfoDTO.from(payInfo));
            accountService.tryPay(PayAccountInfoDTO.from(payInfo));
            payManager.confirming(); //主事务表：confirming
            confirmPay(payInfo);
        } catch (Exception e) {
            payManager.canceling(); //主事务表：canceling，这里不操作也不影响补偿
            cancelPay(payInfo);
        }
    }

    /**
     * 尝试confirm：此前主事务表已经为confirming状态
     * 各confirm需要幂等，都调用成功才算成功，主事务表删除
     *
     * @param payInfo payInfo
     */
    public void confirmPay(PayInfo payInfo) {
        orderService.confirmPay(PayOrderInfoDTO.from(payInfo));
        accountService.confirmPay(PayAccountInfoDTO.from(payInfo));
        payManager.confirmPay(payInfo);
    }

    /**
     * cancel 幂等，都调用成功才算成功，主事务表删除
     * @param payInfo payInfo
     */
    public void cancelPay(PayInfo payInfo) {
        orderService.cancelPay(PayOrderInfoDTO.from(payInfo));
        accountService.cancelPay(PayAccountInfoDTO.from(payInfo));
        payManager.cancelPay(payInfo);
    }

}
