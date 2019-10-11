package com.wcyv90.x.tcc.tx.core;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "x.tcc")
public class TccProperty {

    /**
     * 定时补偿任务corn表达式
     */
    private String corn = "0/30 * * * * ?";

    /**
     * 距离上次更新时间超过xx秒的任务才触发补偿
     */
    private int recoveryInterval = 20;

    private boolean enableRandomDelay = false;

    private int randomDelayRange = 20;

    public String getCorn() {
        return corn;
    }

    public void setCorn(String corn) {
        this.corn = corn;
    }

    public int getRecoveryInterval() {
        return recoveryInterval;
    }

    public void setRecoveryInterval(int recoveryInterval) {
        this.recoveryInterval = recoveryInterval;
    }

    public boolean isEnableRandomDelay() {
        return enableRandomDelay;
    }

    public void setEnableRandomDelay(boolean enableRandomDelay) {
        this.enableRandomDelay = enableRandomDelay;
    }

    public int getRandomDelayRange() {
        return randomDelayRange;
    }

    public void setRandomDelayRange(int randomDelayRange) {
        this.randomDelayRange = randomDelayRange;
    }

}
