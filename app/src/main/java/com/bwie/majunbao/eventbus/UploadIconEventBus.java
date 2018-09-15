package com.bwie.majunbao.eventbus;

public class UploadIconEventBus {
    public String headIcon;

    public UploadIconEventBus(String headIcon) {
        this.headIcon = headIcon;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }

    @Override
    public String toString() {
        return "UploadIconEventBus{" +
                "headIcon='" + headIcon + '\'' +
                '}';
    }
}
