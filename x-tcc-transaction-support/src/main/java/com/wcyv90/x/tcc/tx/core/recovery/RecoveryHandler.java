package com.wcyv90.x.tcc.tx.core.recovery;

public interface RecoveryHandler {

    /**
     * 获取事件类型
     *
     * @return 补偿事件类型
     */
    String getCompensationEvent();

    /**
     * 确认事务
     *
     * @param compensationInfo 补偿信息
     */
    void confirm(String compensationInfo);

    /**
     * 取消事务
     *
     * @param compensationInfo 补偿信息
     */
    void cancel(String compensationInfo);

}
