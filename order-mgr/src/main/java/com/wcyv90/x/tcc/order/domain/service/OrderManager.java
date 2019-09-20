package com.wcyv90.x.tcc.order.domain.service;

import com.wcyv90.x.tcc.order.domain.model.Order;
import com.wcyv90.x.tcc.order.domain.model.PayInfo;

public interface OrderManager {

    Order tryPayOrder(PayInfo payInfo);

    void confirmPayOrder(PayInfo payInfo);

    void cancelPayOrder(PayInfo payInfo);

}
