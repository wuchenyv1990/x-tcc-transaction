package com.wcyv90.x.tcc.tx.core;

import java.time.LocalDateTime;

public class TccTransaction {

    private Long id;

    private String tccTxId;

    private Phase phase = Phase.TRYING;

    private Type type = Type.ROOT;

    /**
     * 分派补偿动作
     */
    private String compensationEvent;

    /**
     * 对应补偿可能使用的数据
     */
    private String compensationInfo;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long retryTimes = 0L;

    /**
     * 事务阶段
     */
    public enum Phase {
        TRYING,
        CONFIRMING,
        CANCELING
    }

    /**
     * 自动恢复从主事务触发
     */
    public enum Type {
        ROOT,
        BRANCH
    }

    public String getTccTxId() {
        return tccTxId;
    }

    public void setTccTxId(String tccTxId) {
        this.tccTxId = tccTxId;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getCompensationEvent() {
        return compensationEvent;
    }

    public void setCompensationEvent(String compensationEvent) {
        this.compensationEvent = compensationEvent;
    }

    public String getCompensationInfo() {
        return compensationInfo;
    }

    public void setCompensationInfo(String compensationInfo) {
        this.compensationInfo = compensationInfo;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Long getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(Long retryTimes) {
        this.retryTimes = retryTimes;
    }

    public void retried() {
        retryTimes++;
    }

    @Override
    public String toString() {
        return "TccTransaction{" +
                "id=" + id +
                ", tccTxId='" + tccTxId + '\'' +
                ", phase=" + phase +
                ", type=" + type +
                ", compensationEvent='" + compensationEvent + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", retryTimes=" + retryTimes +
                '}';
    }
}
