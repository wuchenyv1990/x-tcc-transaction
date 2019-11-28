package com.wcyv90.x.tcc.tx.feign;

import com.wcyv90.x.tcc.common.JsonMapper;
import com.wcyv90.x.tcc.tx.core.TccContext;
import com.wcyv90.x.tcc.tx.core.TccTransaction;
import com.wcyv90.x.tcc.tx.core.TccTransactionManager;
import feign.RequestInterceptor;
import feign.RequestTemplate;

import static com.wcyv90.x.tcc.tx.core.TccEnvFilter.TCC_HEADER;

public class TccContextInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        TccTransaction tccTx = TccTransactionManager.currentTccTx();
        if (tccTx != null) {
            TccContext tccContext = new TccContext();
            tccContext.setTccTxId(tccTx.getTccTxId());
            tccContext.setPhase(tccTx.getPhase());
            tccContext.setEvent(tccTx.getCompensationEvent());
            requestTemplate.header(TCC_HEADER, JsonMapper.dumps(tccContext));
        }
    }

}
