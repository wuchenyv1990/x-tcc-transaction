package com.wcyv90.x.tcc.sponsor.service;

import com.wcyv90.x.tcc.common.JsonMapper;
import com.wcyv90.x.tcc.sponsor.domain.model.PayInfo;
import com.wcyv90.x.tcc.sponsor.domain.service.PayManager;
import com.wcyv90.x.tcc.tx.TccTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.wcyv90.x.tcc.sponsor.infra.Constant.PAY_EVENT;

@Service
public class PayManagerImpl implements PayManager {

    @Autowired
    TccTransactionManager tccTransactionManager;

    /**
     * 数据操作成功则进入trying状态
     *
     * @param payInfo payInfo
     */
    @Override
    @Transactional
    public void tryPay(PayInfo payInfo) {
        tccTransactionManager.createTccTx(PAY_EVENT, JsonMapper.dumps(payInfo));
        // 可能的本地事务
    }

    /**
     * 全部try成功进入confirming状态
     */
    @Override
    @Transactional
    public void confirming() {
        tccTransactionManager.confirming();
    }

    /**
     * 全部confirm成功，删除事务表
     *
     * @param payInfo payInfo
     */
    @Override
    @Transactional
    public void confirmPay(PayInfo payInfo) {
        tccTransactionManager.done();
    }

    /**
     * 进入cenceling状态
     */
    @Override
    @Transactional
    public void canceling() {
        tccTransactionManager.canceling();
    }

    /**
     * 补偿成功，删除事务表
     *
     * @param payInfo
     */
    @Override
    @Transactional
    public void cancelPay(PayInfo payInfo) {
        // 本地事务补偿
        tccTransactionManager.done();
    }




}
