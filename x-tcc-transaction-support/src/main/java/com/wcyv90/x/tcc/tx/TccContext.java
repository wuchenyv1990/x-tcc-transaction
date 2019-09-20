package com.wcyv90.x.tcc.tx;

public class TccContext {

    public static final String TCC_HEADER = "X-tcc-context";

    private String tccTxId;

    private TccTransaction.Phase phase;

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
}
