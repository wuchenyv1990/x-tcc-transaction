package com.wcyv90.x.tcc.tx.core.recovery;

import com.wcyv90.x.tcc.tx.core.TccProperty;
import com.wcyv90.x.tcc.tx.core.TccTransaction;
import com.wcyv90.x.tcc.tx.core.TccTransactionManager;
import com.wcyv90.x.tcc.tx.db.TccTransactionRepo;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.apache.commons.lang.math.RandomUtils;
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
import java.util.concurrent.TimeUnit;
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

    @Autowired
    private RecoveryJob recoveryJob;

    /**
     * 补偿tcc事务表创建时间大于时间间隔的事务
     */
    private int interval = 20;

    private Map<String, RecoveryHandler> recoveryHandlerMap = Collections.emptyMap();

    @PostConstruct
    private void decideIntervalAndLockName() {
        if (tccProperty.getRecoveryInterval() > 20) {
            interval = tccProperty.getRecoveryInterval();
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
        recoveryTccScheduler.schedule(recoveryJob::compensate, new CronTrigger(tccProperty.getCorn()));
    }

    @SchedulerLock(name = "tccRecoveryLock", lockAtMostFor = "1m", lockAtLeastFor = "20s")
    public void compensate() {
        if (tccProperty.isEnableRandomDelay()) {
            sleep();
        }
        LOGGER.debug("RecoveryJob start");
        tccTransactionRepo.getUncompensatedTxs()
                .stream()
                .filter(tccTransaction -> LocalDateTime.now()
                        .minus(interval, ChronoUnit.SECONDS)
                        .compareTo(tccTransaction.getUpdateTime()) > 0
                )
                .filter(this::hasRecoveryHandler)
                .map(TccTransaction::getTccTxId)
                .forEach(this::doCompensate);
    }

    private void sleep() {
        try {
            TimeUnit.SECONDS.sleep(RandomUtils.nextInt(
                    tccProperty.getRandomDelayRange())
            );
        } catch (InterruptedException ignore) {
        }
    }

    private void doCompensate(String tccTxId) {
        try {
            TccTransaction tccTransaction = tccTransactionRepo.queryAndCompensate(tccTxId);
            LOGGER.debug("Compensate event: {}, phase: {}",
                    tccTransaction.getCompensationEvent(),
                    tccTransaction.getPhase()
            );
            TccTransactionManager.setContext(tccTransaction);
            if (tccTransaction.getPhase().equals(CONFIRMING)) {
                tccTransactionManager.confirm(() ->
                        recoveryHandlerMap.get(tccTransaction.getCompensationEvent())
                        .confirm(tccTransaction.getCompensationInfo()));
            } else {
                tccTransactionManager.cancel(() ->
                        recoveryHandlerMap.get(tccTransaction.getCompensationEvent())
                        .cancel(tccTransaction.getCompensationInfo()));
            }
        } catch (Exception e) {
            LOGGER.error("Error occurred when doing compensation: {}.", e.getMessage());
        } finally {
            TccTransactionManager.clearContext();
        }
    }

    private boolean hasRecoveryHandler(TccTransaction tccTransaction) {
        return recoveryHandlerMap.containsKey(tccTransaction);
    }

}
