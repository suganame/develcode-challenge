package com.suganame.payment_gateway.modules.order.enums;

public enum OrderStatus {
    IN_PROGRESS(0),
    CANCELED(-1),
    DONE(1);

    private int status;

    OrderStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
