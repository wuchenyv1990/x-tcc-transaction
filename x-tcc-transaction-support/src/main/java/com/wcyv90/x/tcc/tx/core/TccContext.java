package com.wcyv90.x.tcc.tx.core;

public class TccContext {

    private String tccTxId;

    private TccTransaction.Phase phase;

    private String event;

    public String getTccTxId() {
        return tccTxId;
    }

    public void setTccTxId(String tccTxId) {
        this.tccTxId = tccTxId;
    }

    public TccTransaction.Phase getPhase() {
        return phase;
    }

    public void setPhase(TccTransaction.Phase phase) {
        this.phase = phase;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
