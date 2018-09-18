package com.bwie.majunbao.eventbus;

public class DelEventBus {
    public String pid;

    public DelEventBus(String pid) {
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
        return "DelEventBus{" +
                "pid='" + pid + '\'' +
                '}';
    }
}
