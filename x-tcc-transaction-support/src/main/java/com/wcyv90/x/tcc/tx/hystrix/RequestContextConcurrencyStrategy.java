package com.wcyv90.x.tcc.tx.hystrix;

import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.wcyv90.x.tcc.tx.core.TccTransaction;
import com.wcyv90.x.tcc.tx.core.TccTransactionManager;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.concurrent.Callable;

public class RequestContextConcurrencyStrategy extends HystrixConcurrencyStrategy {

    @Override
    public <T> Callable<T> wrapCallable(Callable<T> callable) {
        return super.wrapCallable(callable);
    }

    static class RequestContextTransmitter<T> implements Callable<T> {

        private final Callable<T> delegate;
        private final RequestAttributes requestAttributes;
        private final TccTransaction tccTransaction;

        public RequestContextTransmitter(Callable<T> callable) {
            this.delegate = callable;
            this.requestAttributes = RequestContextHolder.getRequestAttributes();
            this.tccTransaction = TccTransactionManager.currentTccTx();
        }

        @Override
        public T call() throws Exception {
            boolean setted = false;
            try {
                //信号量隔离不需要操作
                if (RequestContextHolder.getRequestAttributes() == null) {
                    RequestContextHolder.setRequestAttributes(requestAttributes);
                    TccTransactionManager.setContext(tccTransaction);
                    setted = true;
                }
                return delegate.call();
            } finally {
                if (setted) {
                    TccTransactionManager.clearContext();
                    RequestContextHolder.resetRequestAttributes();
                }
            }
        }
    }

}
