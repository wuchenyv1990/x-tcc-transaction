package com.wcyv90.x.tcc.tx.core.recovery;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

public class RecoveryJobConfiguration  {

    @Bean
    public RecoveryJob defaultRecoveryTccJob() {
        return new RecoveryJob();
    }

    @Bean
    public ThreadPoolTaskScheduler recoveryTccScheduler() {
        ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
        executor.setPoolSize(1);
        executor.setThreadNamePrefix("recovery-tcc-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        return executor;
    }

}
