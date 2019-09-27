package com.wcyv90.x.tcc.sponsor.service;

import com.wcyv90.x.tcc.common.JsonMapper;
import com.wcyv90.x.tcc.sponsor.domain.dto.PayAccountInfoDTO;
import com.wcyv90.x.tcc.sponsor.domain.dto.PayOrderInfoDTO;
import com.wcyv90.x.tcc.sponsor.domain.model.PayInfo;
import com.wcyv90.x.tcc.sponsor.domain.service.PayManager;
import com.wcyv90.x.tcc.sponsor.domain.service.PayService;
import com.wcyv90.x.tcc.sponsor.service.client.AccountService;
import com.wcyv90.x.tcc.sponsor.service.client.OrderService;
import com.wcyv90.x.tcc.tx.core.TccTransactionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.wcyv90.x.tcc.sponsor.service.RecoveryPayHandler.PAY_EVENT;

@Service
@Slf4j
public class PayServiceImpl implements PayService {

    @Autowired
    PayManager payManager;

    @Autowired
    OrderService orderService;

    @Autowired
    AccountService accountService;

    @Autowired
    TccTransactionManager tccTransactionManager;

    /**
     * try 主事务try成功才继续
     *
     * @param payInfo payInfo
     */
    @Override
    public void pay(PayInfo payInfo) {

        //可能提前准备一些各服务间所需的交互数据，then:

        tccTransactionManager.withRootTcc(PAY_EVENT, JsonMapper.dumps(payInfo),
                () -> {
                    log.info("Root tcc local action.");
                    payManager.tryPay(payInfo);
                },
                () -> {
                    log.info("Root tcc remote call.");
                    orderService.tryPay(PayOrderInfoDTO.from(payInfo));
                    accountService.tryPay(PayAccountInfoDTO.from(payInfo));
                    log.info("Root tcc remote call success.");
                },
                () -> confirmPay(payInfo),
                () -> cancelPay(payInfo)
        );
    }

    /**
     * 尝试confirm：此前主事务表已经为confirming状态
     * 各confirm需要幂等，都调用成功才算成功，主事务表删除
     *
     * @param payInfo payInfo
     */
    public void confirmPay(PayInfo payInfo) {
        log.info("Root confirm.");
        orderService.confirmPay(PayOrderInfoDTO.from(payInfo));
        accountService.confirmPay(PayAccountInfoDTO.from(payInfo));
        payManager.confirmPay(payInfo);
        log.info("Root confirm success.");
    }

    /**
     * cancel 幂等，都调用成功才算成功，主事务表删除
     *
     * @param payInfo payInfo
     */
    public void cancelPay(PayInfo payInfo) {
        log.info("Root cancel.");
        orderService.cancelPay(PayOrderInfoDTO.from(payInfo));
        accountService.cancelPay(PayAccountInfoDTO.from(payInfo));
        payManager.cancelPay(payInfo);
        log.info("Root cancel success.");
    }

}
