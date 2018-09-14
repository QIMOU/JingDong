package com.bwie.majunbao.eventbus;

public class LoginEventBus {
    public String userId;
    public String sessionId;
    public String nickNick;
    public String sex;
    public String Phone;
    public String headIcon;

    public LoginEventBus(String userId, String sessionId, String nickNick, String sex, String phone, String headIcon) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.nickNick = nickNick;
        this.sex = sex;
        Phone = phone;
        this.headIcon = headIcon;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getNickNick() {
        return nickNick;
    }

    public void setNickNick(String nickNick) {
        this.nickNick = nickNick;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }

    @Override
    public String toString() {
        return "LoginEventBus{" +
                "userId='" + userId + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", nickNick='" + nickNick + '\'' +
                ", sex='" + sex + '\'' +
                ", Phone='" + Phone + '\'' +
                ", headIcon='" + headIcon + '\'' +
                '}';
    }
}
