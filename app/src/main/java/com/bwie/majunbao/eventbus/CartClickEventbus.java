package com.bwie.majunbao.eventbus;

public class CartClickEventbus {
    private String pid;
    private String sellerid;
    private String selected;
    private String num;

    public CartClickEventbus(String pid, String sellerid, String selected, String num) {
        this.pid = pid;
        this.sellerid = sellerid;
        this.selected = selected;
        this.num = num;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getSellerid() {
        return sellerid;
    }

    public void setSellerid(String sellerid) {
        this.sellerid = sellerid;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "CartClickEventbus{" +
                "pid='" + pid + '\'' +
                ", sellerid='" + sellerid + '\'' +
                ", selected='" + selected + '\'' +
                ", num='" + num + '\'' +
                '}';
    }
}
