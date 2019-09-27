package com.wcyv90.x.tcc.order.service.mapper;

import com.wcyv90.x.tcc.order.domain.model.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface OrderMapper {

    Optional<Order> getByIdForUpdate(Long id);

    int update(@Param("order") Order order);

}
