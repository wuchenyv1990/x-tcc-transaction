package com.wcyv90.x.tcc.account.service.mapper;

import com.wcyv90.x.tcc.account.domain.model.Account;
import com.wcyv90.x.tcc.common.util.AmountDecimal;
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
public class AccountMapperTest {

    @Autowired
    AccountMapper accountMapper;

    @Test
    @Transactional(readOnly = true)
    public void shouldGetByIdSuccess() {
        Optional<Account> resultOpt = accountMapper.getByIdForUpdate(1L);
        assertNotNull(resultOpt);
        assertNotNull(resultOpt.get());
    }

    @Test
    @Transactional
    public void shouldUpdateSuccess() {
        BigDecimal totalAmount = AmountDecimal.defaultPrecision("450");
        Account updatedOrder = Account.builder().id(1L).totalAmount(totalAmount).build();
        accountMapper.update(updatedOrder);
        assertEquals(totalAmount, accountMapper.getByIdForUpdate(1L).get().getTotalAmount());
    }

}
