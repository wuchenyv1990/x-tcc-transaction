package com.wcyv90.x.tcc.order.service;

import com.wcyv90.x.tcc.common.exception.AppException;
import com.wcyv90.x.tcc.order.domain.model.Order;
import com.wcyv90.x.tcc.order.domain.model.PayOrderInfo;
import com.wcyv90.x.tcc.order.domain.service.OrderManager;
import com.wcyv90.x.tcc.order.domain.service.OrderRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.wcyv90.x.tcc.common.util.ExceptionGenerator.probablyThrow;

@Service
@Slf4j
public class OrderManagerImpl implements OrderManager {

    @Autowired
    private OrderRepo orderRepo;

    @Override
    public void tryPayOrder(PayOrderInfo payOrderInfo) {
        log.info("Try: orderId={}, amount = {}",
                payOrderInfo.getOrderId(),
                payOrderInfo.getAmount()
        );
        probablyThrow();

        Order order = orderRepo.getById(payOrderInfo.getOrderId()).orElseThrow(AppException::new);
        BigDecimal addedAmount = order.getPaidAmount().add(payOrderInfo.getAmount());
        if (addedAmount.compareTo(order.getPriceAmount()) > 0) {
            throw new AppException();
        }
        log.info("set order {} -> {}", order.getPaidAmount(), addedAmount);

        order.setPaidAmount(addedAmount);
        orderRepo.update(order);
        log.info("Try success.");
    }

    @Override
    public void confirmPayOrder(PayOrderInfo payOrderInfo) {
        log.info("Confirm: orderId={}, amount = {}",
                payOrderInfo.getOrderId(),
                payOrderInfo.getAmount()
        );
        probablyThrow();
    }

    @Override
    public void cancelPayOrder(PayOrderInfo payOrderInfo) {
        log.info("Cancel: orderId={}, amount = {}",
                payOrderInfo.getOrderId(),
                payOrderInfo.getAmount()
        );
        probablyThrow();
        Order order = orderRepo.getById(payOrderInfo.getOrderId()).orElseThrow(AppException::new);
        BigDecimal subtractedAmount = order.getPaidAmount().subtract(payOrderInfo.getAmount());
        log.info("cancel order {} -> {}", order.getPaidAmount(), subtractedAmount);

        order.setPaidAmount(subtractedAmount);
        orderRepo.update(order);
        log.info("Cancel success.");
    }

}
