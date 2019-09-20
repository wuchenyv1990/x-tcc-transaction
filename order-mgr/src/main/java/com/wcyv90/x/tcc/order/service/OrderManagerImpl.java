package com.wcyv90.x.tcc.order.service;

import com.wcyv90.x.tcc.common.exception.AppException;
import com.wcyv90.x.tcc.order.domain.model.Order;
import com.wcyv90.x.tcc.order.domain.model.PayInfo;
import com.wcyv90.x.tcc.order.domain.service.OrderManager;
import com.wcyv90.x.tcc.order.domain.service.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class OrderManagerImpl implements OrderManager {

    @Autowired
    private OrderRepo orderRepo;

    @Override
    @Transactional
    public Order tryPayOrder(PayInfo payInfo) {
        Order order = orderRepo.getById(payInfo.getOrderId()).orElseThrow(AppException::new);
        BigDecimal addedAmount = order.getPaidAmount().add(payInfo.getAmount());
        if (addedAmount.compareTo(order.getPriceAmount()) > 0) {
            throw new AppException();
        }
        order.setPaidAmount(addedAmount);
        orderRepo.update(order);
        return order;
    }

    @Override
    public void confirmPayOrder(PayInfo payInfo) {
    }

    @Override
    public void cancelPayOrder(PayInfo payInfo) {
        Order order = orderRepo.getById(payInfo.getOrderId()).orElseThrow(AppException::new);
        order.setPaidAmount(order.getPaidAmount().subtract(payInfo.getAmount()));
    }
}
