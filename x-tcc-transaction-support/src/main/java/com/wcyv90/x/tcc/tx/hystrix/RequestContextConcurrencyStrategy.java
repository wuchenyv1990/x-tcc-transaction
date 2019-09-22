package com.wcyv90.x.tcc.tx.hystrix;

import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
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

        public RequestContextTransmitter(Callable<T> callable) {
            this.delegate = callable;
            this.requestAttributes = RequestContextHolder.getRequestAttributes();
        }

        @Override
        public T call() throws Exception {
            boolean setted = false;
            try {
                //信号量隔离不需要操作
                if (RequestContextHolder.getRequestAttributes() == null) {
                    RequestContextHolder.setRequestAttributes(requestAttributes);
                    setted = true;
                }
                return delegate.call();
            } finally {
                if (setted) {
                    RequestContextHolder.resetRequestAttributes();
                }
            }
        }
    }

}
