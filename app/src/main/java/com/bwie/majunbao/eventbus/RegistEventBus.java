package com.bwie.majunbao.eventbus;

public class RegistEventBus {
    public String phone;
    public String pwd;
    public String sex;
    public String birthday;

    public RegistEventBus(String phone, String pwd, String sex, String birthday) {
        this.phone = phone;
        this.pwd = pwd;
        this.sex = sex;
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "RegistEventBus{" +
                "phone='" + phone + '\'' +
                ", pwd='" + pwd + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                '}';
    }
}
