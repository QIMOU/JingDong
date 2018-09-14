package com.bwie.majunbao.eventbus;

public class UploadIconEventBus {
    public String sessionId;
    public String nickName;
    public String headIcon;
    public String Phone;

    public UploadIconEventBus(String sessionId, String nickName, String headIcon, String phone) {
        this.sessionId = sessionId;
        this.nickName = nickName;
        this.headIcon = headIcon;
        Phone = phone;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    @Override
    public String toString() {
        return "UploadIconEventBus{" +
                "sessionId='" + sessionId + '\'' +
                ", nickName='" + nickName + '\'' +
                ", headIcon='" + headIcon + '\'' +
                ", Phone='" + Phone + '\'' +
                '}';
    }
}
