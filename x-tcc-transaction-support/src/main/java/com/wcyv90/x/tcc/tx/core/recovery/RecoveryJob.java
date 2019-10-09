package com.wcyv90.x.tcc.tx.core.recovery;

import com.wcyv90.x.tcc.tx.core.TccProperty;
import com.wcyv90.x.tcc.tx.core.TccTransaction;
import com.wcyv90.x.tcc.tx.core.TccTransactionManager;
import com.wcyv90.x.tcc.tx.db.TccTransactionRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import static com.wcyv90.x.tcc.tx.core.TccTransaction.Phase.CONFIRMING;
import static java.util.function.Function.identity;

public class RecoveryJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecoveryJob.class);

    @Autowired
    private TccProperty tccProperty;

    @Autowired
    private TccTransactionRepo tccTransactionRepo;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ThreadPoolTaskScheduler recoveryTccScheduler;

    @Autowired
    private TccTransactionManager tccTransactionManager;

    /**
     * 补偿tcc事务表创建时间大于时间间隔的事务
     */
    private long interval = 20L;

    private Map<String, RecoveryHandler> recoveryHandlerMap = Collections.emptyMap();

    @PostConstruct
    private void decideInterval() {
        if (Long.parseLong(tccProperty.getRecoveryInterval()) > 20L) {
            interval = Long.parseLong(tccProperty.getRecoveryInterval());
        }
    }

    @PostConstruct
    private void prepareRecoveryHandler() {
        recoveryHandlerMap = applicationContext.getBeansOfType(RecoveryHandler.class)
                .values()
                .stream()
                .collect(Collectors.toMap(RecoveryHandler::getCompensationEvent, identity()));
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startJob(ApplicationReadyEvent readyEvent) {
        recoveryTccScheduler.schedule(this::compensate, new CronTrigger(tccProperty.getCorn()));
    }

    private void compensate() {
        LOGGER.debug("RecoveryJob start");
        tccTransactionRepo.getUncompensatedTxs()
                .stream()
                .filter(tccTransaction -> LocalDateTime.now()
                        .minus(interval, ChronoUnit.SECONDS)
                        .compareTo(tccTransaction.getUpdateTime()) > 0
                ).forEach(this::doCompensate);
    }

    private void doCompensate(TccTransaction tccTransaction) {
        LOGGER.info("Compensate event: {}, phase: {}",
                tccTransaction.getCompensationEvent(),
                tccTransaction.getPhase());
        TccTransactionManager.setContext(tccTransaction);
        try {
            tccTransaction.retried();
            tccTransactionRepo.updateTccTransaction(tccTransaction);
            if (tccTransaction.getPhase().equals(CONFIRMING)) {
                tccTransactionManager.confirm(() -> recoveryHandlerMap.getOrDefault(
                        tccTransaction.getCompensationEvent(),
                        new DoNothingRecoveryHandler()
                        ).confirm(tccTransaction.getCompensationInfo())
                );
            } else {
                tccTransactionManager.cancel(() -> recoveryHandlerMap.getOrDefault(
                        tccTransaction.getCompensationEvent(),
                        new DoNothingRecoveryHandler()
                ).cancel(tccTransaction.getCompensationInfo()));
            }
        } catch (Exception e) {
            LOGGER.error("Error occurred when doing compensation: {}.", e.getMessage());
        } finally {
            TccTransactionManager.clearContext();
        }
    }

    private static class DoNothingRecoveryHandler implements RecoveryHandler {

        @Override
        public String getCompensationEvent() {
            return "";
        }

        @Override
        public void confirm(String compensationInfo) {
            LOGGER.info("RecoveryHandler not found");
        }

        @Override
        public void cancel(String compensationInfo) {
            LOGGER.info("RecoveryHandler not found");
        }

    }

}
