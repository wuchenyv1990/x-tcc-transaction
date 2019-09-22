package com.wcyv90.x.tcc.order.service;

import com.wcyv90.x.tcc.common.exception.AppException;
import com.wcyv90.x.tcc.order.domain.model.Order;
import com.wcyv90.x.tcc.order.domain.model.PayOrderInfo;
import com.wcyv90.x.tcc.order.domain.service.OrderManager;
import com.wcyv90.x.tcc.order.domain.service.OrderRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class OrderManagerImpl implements OrderManager {

    private final OrderRepo orderRepo;

    public OrderManagerImpl(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    @Override
    @Transactional
    public Order tryPayOrder(PayOrderInfo payOrderInfo) {
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
    }

    @Override
    public void cancelPayOrder(PayOrderInfo payOrderInfo) {
        Order order = orderRepo.getById(payOrderInfo.getOrderId()).orElseThrow(AppException::new);
        order.setPaidAmount(order.getPaidAmount().subtract(payOrderInfo.getAmount()));
    }
}
