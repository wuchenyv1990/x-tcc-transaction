package com.wcyv90.x.tcc.order.service;

import com.wcyv90.x.tcc.order.domain.model.Order;
import com.wcyv90.x.tcc.order.domain.service.OrderRepo;
import com.wcyv90.x.tcc.order.service.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class OrderRepoImpl implements OrderRepo {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Optional<Order> getById(Long id) {
        return orderMapper.getById(id);
    }

    @Override
    public boolean update(Order order) {
        return orderMapper.update(order) != 0;
    }

}
