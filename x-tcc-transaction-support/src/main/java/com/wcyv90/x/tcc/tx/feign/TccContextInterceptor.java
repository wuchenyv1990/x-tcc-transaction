package com.wcyv90.x.tcc.tx.feign;

import com.wcyv90.x.tcc.common.JsonMapper;
import com.wcyv90.x.tcc.tx.TccContext;
import com.wcyv90.x.tcc.tx.TccTransaction;
import com.wcyv90.x.tcc.tx.TccTransactionManager;
import feign.RequestInterceptor;
import feign.RequestTemplate;

public class TccContextInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        TccTransaction tccTx = TccTransactionManager.currentTccTx();
        if (tccTx != null) {
            TccContext tccContext = new TccContext();
            tccContext.setTccTxId(tccTx.getTccTxId());
            tccContext.setPhase(tccTx.getPhase());
            requestTemplate.header(TccContext.TCC_HEADER, JsonMapper.dumps(tccContext));
        }
    }

}
