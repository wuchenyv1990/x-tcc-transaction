package com.wcyv90.x.tcc.tx.core.recovery;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import net.javacrumbs.shedlock.provider.redis.spring.RedisLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.sql.DataSource;

@EnableSchedulerLock(defaultLockAtLeastFor = "0s", defaultLockAtMostFor = "30s")
public class RecoveryJobConfiguration {

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

    @Bean
    public LockProvider lockProvider(DataSource dataSource) {
        return new JdbcTemplateLockProvider(
                JdbcTemplateLockProvider.Configuration.builder()
                        .withJdbcTemplate(new JdbcTemplate(dataSource))
                        .usingDbTime()
                        .build()
        );
    }

    @Bean
    @Primary
    @ConditionalOnClass(RedisConnectionFactory.class)
    public LockProvider lockProvider(
            @Value("${spring.application.name:${random.value}}") String lockOwner,
            RedisConnectionFactory connectionFactory
    ) {
        return new RedisLockProvider(connectionFactory, lockOwner);
    }

}
