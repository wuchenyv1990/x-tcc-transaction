package com.wcyv90.x.tcc.order.service;

import com.wcyv90.x.tcc.common.JsonMapper;
import com.wcyv90.x.tcc.common.exception.AppException;
import com.wcyv90.x.tcc.order.domain.model.Order;
import com.wcyv90.x.tcc.order.domain.model.PayOrderInfo;
import com.wcyv90.x.tcc.order.domain.service.OrderManager;
import com.wcyv90.x.tcc.order.domain.service.OrderRepo;
import com.wcyv90.x.tcc.tx.TccTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static com.wcyv90.x.tcc.order.infra.Constant.PAY_ORDER_EVENT;

@Service
public class OrderManagerImpl implements OrderManager {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private TccTransactionManager tccTransactionManager;

    @Override
    @Transactional
    public Order tryPayOrder(PayOrderInfo payOrderInfo) {
        tccTransactionManager.createTccTx(PAY_ORDER_EVENT, JsonMapper.dumps(payOrderInfo));
        Order order = orderRepo.getById(payOrderInfo.getOrderId()).orElseThrow(AppException::new);
        BigDecimal addedAmount = order.getPaidAmount().add(payOrderInfo.getAmount());
        if (addedAmount.compareTo(order.getPriceAmount()) > 0) {
            throw new AppException();
        }
        order.setPaidAmount(addedAmount);
        orderRepo.update(order);
        return order;
    }

    @Override
    public void confirmPayOrder(PayOrderInfo payOrderInfo) {
        tccTransactionManager.done();
    }

    @Override
    @Transactional
    public void cancelPayOrder(PayOrderInfo payOrderInfo) {
        if (tccTransactionManager.needCancel()) {
            Order order = orderRepo.getById(payOrderInfo.getOrderId()).orElseThrow(AppException::new);
            order.setPaidAmount(order.getPaidAmount().subtract(payOrderInfo.getAmount()));
            tccTransactionManager.done();
        }
    }
}
