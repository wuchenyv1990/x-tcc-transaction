package com.wcyv90.x.tcc.order.service.mapper;


import com.wcyv90.x.tcc.common.util.AmountDecimal;
import com.wcyv90.x.tcc.order.domain.model.Order;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class OrderMapperTest {

    @Autowired
    OrderMapper orderMapper;

    @Test
    @Transactional(readOnly = true)
    public void shouldGetByIdSuccess() {
        Optional<Order> resultOpt = orderMapper.getByIdForUpdate(1L);
        assertNotNull(resultOpt);
        assertNotNull(resultOpt.get());
    }

    @Test
    @Transactional
    public void shouldUpdateSuccess() {
        BigDecimal paidAmount = AmountDecimal.defaultPrecision("10.5");
        Order updatedOrder = Order.builder().id(1L).paidAmount(paidAmount).build();
        orderMapper.update(updatedOrder);
        assertEquals(paidAmount, orderMapper.getByIdForUpdate(1L).get().getPaidAmount());
    }

}