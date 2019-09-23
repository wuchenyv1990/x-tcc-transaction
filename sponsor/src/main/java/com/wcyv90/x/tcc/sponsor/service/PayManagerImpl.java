package com.wcyv90.x.tcc.sponsor.service;

import com.wcyv90.x.tcc.sponsor.domain.model.PayInfo;
import com.wcyv90.x.tcc.sponsor.domain.service.PayManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PayManagerImpl implements PayManager {

    /**
     * 数据操作成功则进入trying状态
     *
     * @param payInfo payInfo
     */
    @Override
    @Transactional
    public void tryPay(PayInfo payInfo) {
        // 可能的本地事务
    }

    /**
     * 全部confirm成功，删除事务表
     *
     * @param payInfo payInfo
     */
    @Override
    @Transactional
    public void confirmPay(PayInfo payInfo) {
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
    }

}
