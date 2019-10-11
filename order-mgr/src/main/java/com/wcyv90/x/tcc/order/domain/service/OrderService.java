package com.wcyv90.x.tcc.order.domain.service;

import com.wcyv90.x.tcc.order.domain.model.PayOrderInfo;

public interface OrderService {

    void tryPayOrder(PayOrderInfo payOrderInfo);

    void confirmPayOrder(PayOrderInfo payOrderInfo);

    void cancelPayOrder(PayOrderInfo payOrderInfo);

}
