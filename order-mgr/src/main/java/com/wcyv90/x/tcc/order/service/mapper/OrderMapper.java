package com.wcyv90.x.tcc.order.service.mapper;

import com.wcyv90.x.tcc.order.domain.model.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface OrderMapper {

    Optional<Order> getById(Long id);

    int update(Order order);

}
