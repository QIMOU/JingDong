package com.bwie.majunbao.eventbus;

public class AddCartNotifyEventbus {
    public String pid;

    public AddCartNotifyEventbus(String pid) {
        this.pid = pid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        return "AddCartNotifyEventbus{" +
                "pid='" + pid + '\'' +
                '}';
    }
}
