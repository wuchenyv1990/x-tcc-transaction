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
    private String recoveryInterval = "20";

    public String getCorn() {
        return corn;
    }

    public void setCorn(String corn) {
        this.corn = corn;
    }

    public String getRecoveryInterval() {
        return recoveryInterval;
    }

    public void setRecoveryInterval(String recoveryInterval) {
        this.recoveryInterval = recoveryInterval;
    }

}
