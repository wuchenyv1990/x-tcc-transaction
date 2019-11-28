package com.wcyv90.x.tcc.order.service;

import com.wcyv90.x.tcc.order.domain.model.PayOrderInfo;
import com.wcyv90.x.tcc.order.domain.service.OrderManager;
import com.wcyv90.x.tcc.order.domain.service.OrderService;
import com.wcyv90.x.tcc.tx.core.TccTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderManager orderManager;

    @Autowired
    TccTransactionManager tccTransactionManager;

    @Override
    public void tryPayOrder(PayOrderInfo payOrderInfo) {
        tccTransactionManager.branchTry(
                payOrderInfo,
                orderManager::tryPayOrder
        );
    }

    @Override
    public void confirmPayOrder(PayOrderInfo payOrderInfo) {
        tccTransactionManager.confirm(() -> orderManager.confirmPayOrder(payOrderInfo));
    }

    @Override
    public void cancelPayOrder(PayOrderInfo payOrderInfo) {
        tccTransactionManager.cancel(() -> orderManager.cancelPayOrder(payOrderInfo));
    }

}
