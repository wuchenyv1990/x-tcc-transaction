package com.wcyv90.x.tcc.order.domain.service;

import com.wcyv90.x.tcc.order.domain.model.Order;

import java.util.Optional;

public interface OrderRepo {

    Optional<Order> getById(Long id);

    boolean update(Order order);

}
