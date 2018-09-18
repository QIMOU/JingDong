package com.bwie.majunbao.eventbus;

public class PayEventBus {
    public String orderId;

    public PayEventBus(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "PayEventBus{" +
                "orderId='" + orderId + '\'' +
                '}';
    }
}
