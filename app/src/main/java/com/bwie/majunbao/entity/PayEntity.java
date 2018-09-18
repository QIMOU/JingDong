package com.bwie.majunbao.entity;

public class PayEntity {
    public String payJson;

    public PayEntity(String payJson) {
        this.payJson = payJson;
    }

    public String getPayJson() {
        return payJson;
    }

    public void setPayJson(String payJson) {
        this.payJson = payJson;
    }

    @Override
    public String toString() {
        return "PayEntity{" +
                "payJson='" + payJson + '\'' +
                '}';
    }
}
