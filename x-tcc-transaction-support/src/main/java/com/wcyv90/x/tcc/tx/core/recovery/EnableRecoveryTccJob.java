package com.wcyv90.x.tcc.tx.core.recovery;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(RecoveryJobConfigurationSelector.class)
public @interface EnableRecoveryTccJob {
}
